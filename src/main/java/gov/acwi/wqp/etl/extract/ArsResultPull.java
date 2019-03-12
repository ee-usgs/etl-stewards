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

import gov.acwi.wqp.etl.extract.domain.ArsResult;
import gov.acwi.wqp.etl.extract.domain.ArsResultProcessor;
import gov.acwi.wqp.etl.extract.domain.WqxActivity;
import gov.acwi.wqp.etl.extract.domain.WqxActivityDescription;
import gov.acwi.wqp.etl.extract.domain.WqxCollectionMethod;
import gov.acwi.wqp.etl.extract.domain.WqxDataQuality;
import gov.acwi.wqp.etl.extract.domain.WqxDetectionQuantitationLimitMeasure;
import gov.acwi.wqp.etl.extract.domain.WqxResult;
import gov.acwi.wqp.etl.extract.domain.WqxResultAnalyticalMethod;
import gov.acwi.wqp.etl.extract.domain.WqxResultDescription;
import gov.acwi.wqp.etl.extract.domain.WqxResultDetectionQuantitationLimit;
import gov.acwi.wqp.etl.extract.domain.WqxResultLabInformation;
import gov.acwi.wqp.etl.extract.domain.WqxResultMeasure;
import gov.acwi.wqp.etl.extract.domain.WqxSampleDescription;
import gov.acwi.wqp.etl.extract.domain.WqxTime;


@Configuration
public class ArsResultPull {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Value("classpath:testData/xml/wqxResult.xml")
	private Resource resource;

	@Autowired
	@Qualifier("truncateArsResult")
	private Tasklet truncateArsResult;

	@Bean
	public StaxEventItemReader<WqxActivity> arsResultReader() {
		StaxEventItemReader<WqxActivity> staxEventItemReader = new StaxEventItemReader<>();
//		try {
//			//This is a state with no data at this time. It will give us back just the Organization and Project data.
//			staxEventItemReader.setResource(new UrlResource("https://www.nrrig.mwa.ars.usda.gov/st40_wqp/service1.svc/result?countrycode=us&statecode=US%3A56"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		staxEventItemReader.setResource(resource);
		staxEventItemReader.setFragmentRootElementName("Activity");
		Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
		unMarshaller.setClassesToBeBound(WqxActivity.class, WqxActivityDescription.class,
				WqxTime.class, WqxSampleDescription.class, WqxCollectionMethod.class,
				WqxResult.class, WqxResultDescription.class, WqxResultMeasure.class,
				WqxDataQuality.class, WqxResultAnalyticalMethod.class, WqxDetectionQuantitationLimitMeasure.class,
				WqxResultLabInformation.class, WqxResultDetectionQuantitationLimit.class
				);
		staxEventItemReader.setUnmarshaller(unMarshaller);
		return staxEventItemReader;
	}

	@Bean
	public ItemWriter<ArsResult> arsResultWriter() {
		JdbcBatchItemWriter<ArsResult> itemWriter = new JdbcBatchItemWriter<ArsResult>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql("insert "
				+ " into ars_result (activity_identifier, activity_type_code, activity_media_name, activity_start_date, activity_start_time, activity_start_time_zone_code,"
					+ " measure_value, measure_unit_code, activity_depth_height_measure, project_identifier, monitoring_location_identifier, activity_comment_text,"
					+ " sample_collection_method_identifier, sample_collection_method_identifier_context, sample_collection_method_name, sample_collection_method_description_text,"
					+ " sample_collection_equipment_name, sample_collection_equipment_comment_text, result_detection_condition_text, characteristic_name, result_sample_fraction_text,"
					+ " result_measure_value, result_measure_unit_code, result_status_identifier, result_value_type_name, data_quality_precision_value, result_comment_text,"
					+ " result_analytical_method_identifier, result_analytical_method_identifier_context, result_analytical_method_name, result_analytical_method_description_text,"
					+ " detection_quantitation_limit_type_name, detection_quantitation_limit_measure_value, detection_quantitation_limit_measure_unit_code)"
				+ " values (:activityIdentifier, :activityTypeCode, :activityMediaName, :activityStartDate, :activityStartTime, :activityStartTimeZoneCode,"
					+ " :measureValue, :measureUnitCode, :activityDepthHeightMeasure, :projectIdentifier, :monitoringLocationIdentifier, :activityCommentText,"
					+ " :sampleCollectionMethodIdentifier, :sampleCollectionMethodIdentifierContext, :sampleCollectionMethodName, :sampleCollectionMethodDescriptionText,"
					+ " :sampleCollectionEquipmentName, :sampleCollectionEquipmentCommentText, :resultDetectionConditionText, :characteristicName, :resultSampleFractionText,"
					+ " :resultMeasureValue, :resultMeasureUnitCode, :resultStatusIdentifier, :resultValueTypeName, :dataQualityPrecisionValue, :resultCommentText,"
					+ " :resultAnalyticalMethodIdentifier, :resultAnalyticalMethodIdentifierContext, :resultAnalyticalMethodName, :resultAnalyticalMethodDescriptionText,"
					+ " :detectionQuantitationLimitTypeName, :detectionQuantitationLimitMeasureValue, :detectionQuantitationLimitMeasureUnitCode)");

		ItemSqlParameterSourceProvider<ArsResult> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();

		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}


	@Bean
	public Step truncateArsResultStep() {
		return stepBuilderFactory
				.get("truncateArsResultStep")
				.tasklet(truncateArsResult)
				.build();
	}
	@Bean
	public Step arsResultPullStep() {
		return stepBuilderFactory.get("arsResultPullStep")
				.<WqxActivity, ArsResult>chunk(10)
				.reader(arsResultReader())
				.processor(new ArsResultProcessor())
				.writer(arsResultWriter())
				.build();
	}

	@Bean
	public Flow arsResultPullFlow() {
		return new FlowBuilder<SimpleFlow>("arsResultPullFlow")
				.start(truncateArsResultStep())
				.next(arsResultPullStep())
				.build();
	}

}
