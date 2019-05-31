package gov.acwi.wqp.etl.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.ArsBaseFlowIT;
import gov.acwi.wqp.etl.EtlConstantUtils;

public class TransformActivityIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + "'activity_swap_stewards'";

	@Autowired
	@Qualifier("activityFlow")
	private Flow activityFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.ANALYZE_ACTIVITY_FLOW)
	private Flow analyzeActivityFlow;

	private Job setupFlowTestJob() {
		return jobBuilderFactory.get("activityFlowTest").start(activityFlow).build().build();
	}

	private Job setupAnalyzeTestJob() {
		return jobBuilderFactory.get("analyzeActivityFlowTest").start(analyzeActivityFlow).build().build();
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/projectData/projectData.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsResult/arsResult.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformActivityStepTest() {
		jobLauncherTestUtils.setJob(setupFlowTestJob());
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformActivityStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/activity.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void analyzeActivityFlowTest() {
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
	@DatabaseSetup(value="classpath:/testData/stewards/activity/activityOld.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/projectData/projectData.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsResult/arsResult.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'activity_swap_stewards'")
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/stewards/activity/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'activity_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/activity.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void activityFlowTest() {
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
