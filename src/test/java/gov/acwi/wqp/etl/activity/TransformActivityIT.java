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

public class TransformActivityIT extends ArsBaseFlowIT {

	@Autowired
	@Qualifier("activityFlow")
	private Flow activityFlow;

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/projectData/projectData.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/result.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformActivityStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformActivityStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/projectData/projectData.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/result.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'activity_swap_stewards'")
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/stewards/activity/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'activity_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void activityFlowTest() {
		Job activityFlowTest = jobBuilderFactory.get("activityFlowTest")
					.start(activityFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(activityFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
