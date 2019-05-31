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
import gov.acwi.wqp.etl.EtlConstantUtils;

public class TransformMonitoringLocationIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + "'station_swap_stewards'";

	@Autowired
	@Qualifier("monitoringLocationFlow")
	private Flow monitoringLocationFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.ANALYZE_MONITORING_LOCATION_FLOW)
	private Flow analyzeMonitoringLocationFlow;

	private Job setupFlowTestJob() {
		return jobBuilderFactory.get("monitoringLocationFlowTest").start(monitoringLocationFlow).build().build();
	}

	private Job setupAnalyzeTestJob() {
		return jobBuilderFactory.get("analyzeMonitoringLocationFlowTest").start(analyzeMonitoringLocationFlow).build().build();
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/monitoringLocation/empty.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/siteTypeToPrimary.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsOrgProject/arsOrgProject.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsMonitoringLocation/arsMonitoringLocation.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformMonitoringLocationStepTest() {
		jobLauncherTestUtils.setJob(setupFlowTestJob());
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformMonitoringLocationStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/monitoringLocation.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void analyzeMonitoringLocationFlowTest() {
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
	@DatabaseSetup(value="classpath:/testData/stewards/monitoringLocation/monitoringLocationOld.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/siteTypeToPrimary.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsOrgProject/arsOrgProject.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsMonitoringLocation/arsMonitoringLocation.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
				table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
				query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'station_swap_stewards'")
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/stewards/monitoringLocation/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
				table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
				query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'station_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/monitoringLocation.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void monitoringLocationFlowTest() {
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
