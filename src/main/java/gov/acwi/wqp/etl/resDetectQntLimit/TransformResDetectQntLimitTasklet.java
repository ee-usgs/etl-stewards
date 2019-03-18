package gov.acwi.wqp.etl.resDetectQntLimit;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class TransformResDetectQntLimitTasklet implements Tasklet {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TransformResDetectQntLimitTasklet(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		jdbcTemplate.execute("insert " + 
				"  into r_detect_qnt_lmt_swap_stewards(data_source_id, data_source, station_id, site_id, event_date, activity, "
				+ "                                      analytical_method,"
				+ " sample_media, organization, site_type, huc, governmental_unit_code,"
				+ "                                      geom, organization_name, activity_id, project_id, result_id,"
				+ "                                    characteristic_name, characteristic_type,"
				//characteristic_type, assemblage_sampled_name, sample_tissue_taxonomic_name, " + 
				+ "                                      detection_limit_id, detection_limit, detection_limit_unit, detection_limit_desc)" + 
				" select data_source_id," + 
				"        data_source," + 
				"        station_id," + 
				"        site_id," + 
				"        event_date," + 
				"        activity," + 
				"        analytical_procedure_id," + 
				"        sample_media," + 
				"        organization," + 
				"        site_type," + 
				"        huc," + 
				"        governmental_unit_code," + 
				"        geom," + 
				"        organization_name," + 
				"        activity_id," + 
				"        project_id," + 
				"        result_id," + 
				"        characteristic_name," + 
				"        characteristic_type," + 
//				"        assemblage_sampled_name," + 
//				"        sample_tissue_taxonomic_name," + 
				"        result_id detection_limit_id," + 
				"        detection_limit," + 
				"        detection_limit_unit," + 
				"        detection_limit_desc" + 
				"   from result_swap_stewards" + 
				"  where detection_limit is not null or" + 
				"        detection_limit_unit is not null or" + 
				"        detection_limit_desc is not null;");
		return RepeatStatus.FINISHED;
	}
}
