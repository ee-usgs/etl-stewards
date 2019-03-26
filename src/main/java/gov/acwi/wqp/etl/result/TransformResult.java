package gov.acwi.wqp.etl.result;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gov.acwi.wqp.etl.stewards.ArsResult;
import gov.acwi.wqp.etl.stewards.ArsResultResultRowMapper;


@Configuration
public class TransformResult {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("setupResultSwapTableFlow")
	private Flow setupResultSwapTableFlow;

	@Autowired
	@Qualifier("buildResultIndexesFlow")
	private Flow buildResultIndexesFlow;

	@Bean
	public JdbcCursorItemReader<ArsResult> resultReader() {
		return new JdbcCursorItemReaderBuilder<ArsResult>()
				.dataSource(this.dataSource)
				.name("organizationReader")
				//TODo cleanup for PostgreSQL
				.sql("select activity_swap_stewards.activity_id,"
						+ "  ars_result.activity_start_date,"
						+ "  ars_result.activity_identifier,"
						+ "  ars_result.activity_media_name,"
						+ "  ars_result.activity_type_code,"
						+ "  ars_result.activity_start_time,"
						+ "  ars_result.activity_start_time_zone_code,"
						+ "  ars_result.project_identifier,"
						+ "  ars_result.sample_collection_method_identifier,"
						+ "  ars_result.sample_collection_method_identifier_context,"
						+ "  ars_result.sample_collection_method_name,"
						+ "  ars_result.sample_collection_method_description_text,"
						+ "  ars_result.sample_collection_equipment_name,"
						+ "  ars_result.sample_collection_equipment_comment_text,"
						+ "  activity_swap_stewards.station_id,"
						+ "  activity_swap_stewards.site_id,"
						+ "  activity_swap_stewards.organization,"
						+ "  activity_swap_stewards.organization_name,"
						+ "  activity_swap_stewards.site_type,"
						+ "  activity_swap_stewards.huc,"
						+ "  activity_swap_stewards.governmental_unit_code,"
						+ "  activity_swap_stewards.geom,"
						+ "  activity_swap_stewards.monitoring_location_name,"
						+ "  activity_swap_stewards.project_name,"
						+ "  ars_result.result_id,"
						+ "  ars_result.result_detection_condition_text,"
						+ "  ars_result.characteristic_name,"
						+ "  ars_result.result_sample_fraction_text,"
						+ "  ars_result.result_measure_value,"
						+ "  ars_result.result_measure_unit_code,"
						+ "  ars_result.result_status_identifier,"
						+ "  ars_result.result_value_type_name,"
						+ "  ars_result.data_quality_precision_value,"
						+ "  ars_result.result_comment_text,"
						+ "  ars_result.result_analytical_method_identifier,"
						+ "  ars_result.result_analytical_method_identifier_context,"
						+ "  ars_result.result_analytical_method_name,"
						+ "  ars_result.result_analytical_method_description_text,"
						+ "  ars_result.detection_quantitation_limit_type_name,"
						+ "  ars_result.detection_quantitation_limit_measure_value,"
						+ "  ars_result.detection_quantitation_limit_measure_unit_code,"
						+ "  ars_char_name_to_type.characteristic_type"
						+ " from ars_result"
						+ "      join activity_swap_stewards"
						+ "        on ars_result.activity_identifier = activity_swap_stewards.activity"
						+ "      left join ars_char_name_to_type"
						+ "        on ars_result.characteristic_name = ars_char_name_to_type.characteristic_name")
				.rowMapper(new ArsResultResultRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<Result> resultWriter() {
		JdbcBatchItemWriter<Result> itemWriter = new JdbcBatchItemWriter<Result>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql("insert "
				+ " into result_swap_stewards (data_source_id, data_source, station_id, site_id, event_date, activity,"
				+ "                                sample_media, organization, site_type, huc, governmental_unit_code, geom,"
				+ "                                organization_name, activity_id, activity_type_code, activity_start_time,"
				+ "                                act_start_time_zone, project_id, project_name, monitoring_location_name,"
				+ "                                sample_collect_method_id, sample_collect_method_ctx, sample_collect_method_name,"
				+ "                                sample_collect_equip_name, result_id, result_detection_condition_tx,"
				+ "                                characteristic_name, characteristic_type, sample_fraction_type, result_measure_value, result_unit,"
				+ "                                result_value_status, result_value_type, precision, result_comment, analytical_procedure_id,"
				+ "                                analytical_procedure_source, analytical_method_name, analytical_method_citation,"
				+ "                                detection_limit, detection_limit_unit, detection_limit_desc)"
				+ "                        values (:dataSourceId, :dataSource, :stationId, :siteId, :eventDate, :activity,"
				+ "                                :sampleMedia, :organization, :siteType, :huc, :governmentalUnitCode, :geom,"
				+ "                                :organizationName, :activityId, :activityTypeCode, :activityStartTime,"
				+ "                                :actStartTimeZone, :projectId, :projectName, :monitoringLocationName,"
				+ "                                :sampleCollectMethodId, :sampleCollectMethodCtx, :sampleCollectMethodName,"
				+ "                                :sampleCollectEquipName, :resultId, :resultDetectionConditionTx,"
				+ "                                :characteristicName, :characteristicType, :sampleFractionType, :resultMeasureValue, :resultUnit,"
				+ "                                :resultValueStatus, :resultValueType, :precision, :resultComment, :analyticalProcedureId,"
				+ "                                :analyticalProcedureSource, :analyticalMethodName, :analyticalMethodCitation,"
				+ "                                :detectionLimit, :detectionLimitUnit, :detectionLimitDesc)");
		ItemSqlParameterSourceProvider<Result> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformResultStep() {
		return stepBuilderFactory
				.get("transformResultStep")
				.<ArsResult, Result>chunk(10)
				.reader(resultReader())
				.processor(new ResultProcessor())
				.writer(resultWriter())
				.build();
	}

	@Bean
	public Flow resultFlow() {
		return new FlowBuilder<SimpleFlow>("resultFlow")
				.start(setupResultSwapTableFlow)
				.next(transformResultStep())
				.next(buildResultIndexesFlow)
				.build();
	}

}
