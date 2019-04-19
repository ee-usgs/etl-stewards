package gov.acwi.wqp.etl.stewards.monitoringLocation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
import org.springframework.util.FileCopyUtils;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.stewards.ArsUrlResource;
import gov.acwi.wqp.etl.stewards.wqx.WqxDrainageAreaMeasure;
import gov.acwi.wqp.etl.stewards.wqx.WqxMonitoringLocation;
import gov.acwi.wqp.etl.stewards.wqx.WqxMonitoringLocationGeospatial;
import gov.acwi.wqp.etl.stewards.wqx.WqxMonitoringLocationIdentity;

@Configuration
public class ArsMonitoringLocationPull {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier(Application.DATASOURCE_ARS_QUALIFIER)
	private DataSource dataSourceArs;

	@Value("${wqx.monitoringLocation}")
	private URL url;

	@Autowired
	@Qualifier("truncateArsMonitoringLocation")
	private Tasklet truncateArsMonitoringLocation;

	@Value("classpath:sql/monitoringLocation/writeArsMonitoringLocation.sql")
	private Resource writerResource;

	@Bean
	public StaxEventItemReader<WqxMonitoringLocation> arsMonitoringLocationReader() throws MalformedURLException {
		StaxEventItemReader<WqxMonitoringLocation> staxEventItemReader = new StaxEventItemReader<>();
		staxEventItemReader.setResource(new ArsUrlResource(url));
		staxEventItemReader.setFragmentRootElementName("MonitoringLocation");
		Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
		unMarshaller.setClassesToBeBound(WqxMonitoringLocation.class, WqxMonitoringLocationIdentity.class,
				WqxDrainageAreaMeasure.class, WqxMonitoringLocationGeospatial.class);
		staxEventItemReader.setUnmarshaller(unMarshaller);
		return staxEventItemReader;
	}

	@Bean
	public ItemWriter<ArsMonitoringLocation> arsMonitoringLocationWriter() throws IOException {
		JdbcBatchItemWriter<ArsMonitoringLocation> itemWriter = new JdbcBatchItemWriter<ArsMonitoringLocation>();
		itemWriter.setDataSource(dataSourceArs);
		itemWriter.setSql(new String(FileCopyUtils.copyToByteArray(writerResource.getInputStream())));

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
	public Step arsMonitoringLocationPullStep() throws IOException {
		return stepBuilderFactory.get("arsMonitoringLocationPullStep")
				.<WqxMonitoringLocation, ArsMonitoringLocation>chunk(10)
				.reader(arsMonitoringLocationReader())
				.processor(new ArsMonitoringLocationProcessor())
				.writer(arsMonitoringLocationWriter())
				.build();
	}

	@Bean
	public Flow arsMonitoringLocationPullFlow() throws IOException {
		return new FlowBuilder<SimpleFlow>("arsMonitoringLocationPullFlow")
				.start(truncateArsMonitoringLocationStep())
				.next(arsMonitoringLocationPullStep())
				.build();
	}

}
