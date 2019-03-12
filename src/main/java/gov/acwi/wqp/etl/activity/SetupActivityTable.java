package gov.acwi.wqp.etl.activity;

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
public class SetupActivityTable implements Tasklet {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public SetupActivityTable(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//TODO Correct tables in PostgreSQL and split
//		jdbcTemplate.execute("drop table if exists org_data_swap_stewards");
//		jdbcTemplate.execute("create unlogged table org_data_swap_stewards like org_data including comments defaults identity with (fillfactor = 100)");
		jdbcTemplate.execute("truncate table activity_swap_stewards");
		return RepeatStatus.FINISHED;
	}
}
