package gov.acwi.wqp.etl.monitoringLocation;

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

public class TransformMonitoringLocationIT extends ArsBaseFlowIT {

	@Autowired
	@Qualifier("monitoringLocationFlow")
	private Flow monitoringLocationFlow;

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/monitoringLocation/empty.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/siteTypeToPrimary.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/orgProject.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/monitoringLocation.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformMonitoringLocationStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformMonitoringLocationStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/stewards/monitoringLocation/monitoringLocationOld.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/siteTypeToPrimary.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/orgProject.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/monitoringLocation.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
				table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
				query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'station_swap_stewards'")
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/stewards/monitoringLocation/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
				table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
				query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'station_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void monitoringLocationFlowTest() {
		Job monitoringLocationFlowTest = jobBuilderFactory.get("monitoringLocationFlowTest")
					.start(monitoringLocationFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(monitoringLocationFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
