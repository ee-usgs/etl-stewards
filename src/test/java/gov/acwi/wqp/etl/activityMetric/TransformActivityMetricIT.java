package gov.acwi.wqp.etl.activityMetric;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.ArsBaseFlowIT;
import gov.acwi.wqp.etl.EtlConstantUtils;

public class TransformActivityMetricIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + "'act_metric_swap_stewards'";

	@Autowired
	@Qualifier("activityMetricFlow")
	private Flow activityMetricFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.ANALYZE_ACTIVITY_METRIC_FLOW)
	private Flow analyzeActivityMetricFlow;

	private Job setupFlowTestJob() {
		return jobBuilderFactory.get("activityMetricFlowTest").start(activityMetricFlow).build().build();
	}

	private Job setupAnalyzeTestJob() {
		return jobBuilderFactory.get("analyzeActivityMetricFlowTest").start(analyzeActivityMetricFlow).build().build();
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/activityMetric.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void analyzeActivityMetricFlowTest() {
		jobLauncherTestUtils.setJob(setupAnalyzeTestJob());
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/stewards/activityMetric/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'act_metric_swap_stewards'")
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/stewards/activityMetric/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'act_metric_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/activityMetric/activityMetric.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/activityMetric.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void activityMetricFlowTest() {
		jobLauncherTestUtils.setJob(setupFlowTestJob());
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
