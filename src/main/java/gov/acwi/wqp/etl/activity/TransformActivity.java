package gov.acwi.wqp.etl.activity;

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
import gov.acwi.wqp.etl.stewards.ArsResultActivityRowMapper;


@Configuration
public class TransformActivity {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("setupActivitySwapTableFlow")
	private Flow setupActivitySwapTableFlow;

	@Autowired
	@Qualifier("buildActivityIndexesFlow")
	private Flow buildActivityIndexesFlow;

	@Bean
	public JdbcCursorItemReader<ArsResult> activityReader() {
		return new JdbcCursorItemReaderBuilder<ArsResult>()
				.dataSource(this.dataSource)
				.name("organizationReader")
				//TODo cleanup for PostgreSQL
				.sql("select row_number() over () activity_id, a.* from (select distinct "
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
						+ "  station_swap_stewards.station_id,"
						+ "  station_swap_stewards.site_id,"
						+ "  station_swap_stewards.organization,"
						+ "  station_swap_stewards.organization_name,"
						+ "  station_swap_stewards.site_type,"
						+ "  station_swap_stewards.huc,"
						+ "  station_swap_stewards.governmental_unit_code,"
						+ "  station_swap_stewards.geom,"
						+ "  station_swap_stewards.station_name,"
						+ "  project_data_swap_stewards.project_name"
						+ " from ars_result"
						+ "      join station_swap_stewards"
						+ "        on ars_result.monitoring_location_identifier = substring(station_swap_stewards.site_id, 5)"
						+ "      join project_data_swap_stewards"
						+ "        on ars_result.project_identifier = project_data_swap_stewards.project_identifier"
						+ "   order by ars_result.activity_identifier) a")
				.rowMapper(new ArsResultActivityRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<Activity> activityWriter() {
		JdbcBatchItemWriter<Activity> itemWriter = new JdbcBatchItemWriter<Activity>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql("insert "
				+ " into activity_swap_stewards (data_source_id, data_source, station_id, site_id, event_date, activity," + 
				"                                sample_media, organization, site_type, huc, governmental_unit_code, geom," + 
				"                                organization_name, activity_id, activity_type_code, activity_start_time," + 
				"                                act_start_time_zone, project_id, project_name, monitoring_location_name," + 
				"                                sample_collect_method_id, sample_collect_method_ctx, sample_collect_method_name, sample_collect_equip_name)" + 
				"                        values (:dataSourceId, :dataSource, :stationId, :siteId, :eventDate, :activity," + 
				"                                :sampleMedia, :organization, :siteType, :huc, :governmentalUnitCode, :geom," + 
				"                                :organizationName, :activityId, :activityTypeCode, :activityStartTime," + 
				"                                :actStartTimeZone, :projectId, :projectName, :monitoringLocationName," + 
				"                                :sampleCollectMethodId, :sampleCollectMethodCtx, :sampleCollectMethodName, :sampleCollectEquipName)");
		ItemSqlParameterSourceProvider<Activity> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformActivityStep() {
		return stepBuilderFactory
				.get("transformActivityStep")
				.<ArsResult, Activity>chunk(10)
				.reader(activityReader())
				.processor(new ActivityProcessor())
				.writer(activityWriter())
				.build();
	}

	@Bean
	public Flow activityFlow() {
		return new FlowBuilder<SimpleFlow>("activityFlow")
				.start(setupActivitySwapTableFlow)
				.next(transformActivityStep())
				.next(buildActivityIndexesFlow)
				.build();
	}

}
