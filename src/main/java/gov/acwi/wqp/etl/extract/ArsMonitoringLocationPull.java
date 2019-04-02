package gov.acwi.wqp.etl.extract;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import gov.acwi.wqp.etl.extract.domain.WqxDrainageAreaMeasure;
import gov.acwi.wqp.etl.extract.domain.WqxMonitoringLocation;
import gov.acwi.wqp.etl.extract.domain.WqxMonitoringLocationGeospatial;
import gov.acwi.wqp.etl.extract.domain.WqxMonitoringLocationIdentity;
import gov.acwi.wqp.etl.stewards.ArsMonitoringLocation;
import gov.acwi.wqp.etl.stewards.ArsMonitoringLocationProcessor;


@Configuration
public class ArsMonitoringLocationPull {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("dataSourceArs")
	private DataSource dataSourceArs;

	@Value("classpath:testData/xml/wqxStation.xml")
	private Resource resource;

	@Autowired
	@Qualifier("truncateArsMonitoringLocation")
	private Tasklet truncateArsMonitoringLocation;

	@Bean
	public StaxEventItemReader<WqxMonitoringLocation> arsMonitoringLocationReader() {
		StaxEventItemReader<WqxMonitoringLocation> staxEventItemReader = new StaxEventItemReader<>();
//		try {
//			//This is a state with no data at this time. It will give us back just the Organization and Project data.
//			staxEventItemReader.setResource(new UrlResource("https://www.nrrig.mwa.ars.usda.gov/st40_wqp/service1.svc/station?countrycode=us&statecode=US%3A56"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		staxEventItemReader.setResource(resource);
		staxEventItemReader.setFragmentRootElementName("MonitoringLocation");
		Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
		unMarshaller.setClassesToBeBound(WqxMonitoringLocation.class, WqxMonitoringLocationIdentity.class,
				WqxDrainageAreaMeasure.class, WqxMonitoringLocationGeospatial.class);
		staxEventItemReader.setUnmarshaller(unMarshaller);
		return staxEventItemReader;
	}

	@Bean
	public ItemWriter<ArsMonitoringLocation> arsMonitoringLocationWriter() {
		JdbcBatchItemWriter<ArsMonitoringLocation> itemWriter = new JdbcBatchItemWriter<ArsMonitoringLocation>();
		itemWriter.setDataSource(dataSourceArs);
		itemWriter.setSql("insert "
				+ " into ars_monitoring_location (monitoring_location_identifier, monitoring_location_name, monitoring_location_type_name, monitoring_location_description_text, huc_eight_digit_code, huc_twelve_digit_code, drainage_area_measure_value, drainage_area_measure_unit_code, latitude_measure, longitude_measure, horizontal_collection_method_name, horizontal_coordinate_reference_system_datum_name, country_code, state_code, county_code)"
				+ " values (:monitoringLocationIdentifier, :monitoringLocationName, :monitoringLocationTypeName, :monitoringLocationDescriptionText, :hucEightDigitCode, :hucTwelveDigitCode, :drainageAreaMeasureValue, :drainageAreaMeasureUnitCode, :latitudeMeasure, :longitudeMeasure, :horizontalCollectionMethodName, :horizontalCoordinateReferenceSystemDatumName, :countryCode, :stateCode, :countyCode)");

		ItemSqlParameterSourceProvider<ArsMonitoringLocation> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();

		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}


	@Bean
	public Step truncateArsMonitoringLocationStep() {
		return stepBuilderFactory
				.get("truncateArsMonitoringLocationStep")
				.tasklet(truncateArsMonitoringLocation)
				.build();
	}
	@Bean
	public Step arsMonitoringLocationPullStep() {
		return stepBuilderFactory.get("arsMonitoringLocationPullStep")
				.<WqxMonitoringLocation, ArsMonitoringLocation>chunk(10)
				.reader(arsMonitoringLocationReader())
				.processor(new ArsMonitoringLocationProcessor())
				.writer(arsMonitoringLocationWriter())
				.build();
	}

	@Bean
	public Flow arsMonitoringLocationPullFlow() {
		return new FlowBuilder<SimpleFlow>("arsMonitoringLocationPullFlow")
				.start(truncateArsMonitoringLocationStep())
				.next(arsMonitoringLocationPullStep())
				.build();
	}

}
