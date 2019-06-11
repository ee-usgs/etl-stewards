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

	public static final String TABLE_NAME = "'station_swap_stewards'";
	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + TABLE_NAME;
	public static final String EXPECTED_DATABASE_QUERY_PRIMARY_KEY = BASE_EXPECTED_DATABASE_QUERY_PRIMARY_KEY
			+ EQUALS_QUERY + TABLE_NAME;
	public static final String EXPECTED_DATABASE_QUERY_FOREIGN_KEY = BASE_EXPECTED_DATABASE_QUERY_FOREIGN_KEY
			+ EQUALS_QUERY + TABLE_NAME;

	@Autowired
	@Qualifier("monitoringLocationFlow")
	private Flow monitoringLocationFlow;

	private Job setupFlowTestJob() {
		return jobBuilderFactory.get("monitoringLocationFlowTest").start(monitoringLocationFlow).build().build();
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/result/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/empty.xml")
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
	@DatabaseSetup(value="classpath:/testResult/stewards/result/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/empty.xml")
	@DatabaseSetup(value="classpath:/testData/stewards/monitoringLocation/monitoringLocationOld.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/siteTypeToPrimary.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsOrgProject/arsOrgProject.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsMonitoringLocation/arsMonitoringLocation.xml")
	@ExpectedDatabase(
			value="classpath:/testResult/stewards/monitoringLocation/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + TABLE_NAME)
	@ExpectedDatabase(
			connection=CONNECTION_INFORMATION_SCHEMA,
			value="classpath:/testResult/stewards/monitoringLocation/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + TABLE_NAME)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(
			value="classpath:/testResult/stewards/analyze/monitoringLocation.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_ANALYZE,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	@ExpectedDatabase(
			value="classpath:/testResult/stewards/monitoringLocation/primaryKey.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_PRIMARY_KEY,
			query=EXPECTED_DATABASE_QUERY_PRIMARY_KEY)
	@ExpectedDatabase(
			value="classpath:/testResult/stewards/monitoringLocation/foreignKey.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_FOREIGN_KEY,
			query=EXPECTED_DATABASE_QUERY_FOREIGN_KEY)
	@ExpectedDatabase(
			value="classpath:/testResult/stewards/monitoringLocation/indexes/pk.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX_PK + TABLE_NAME)
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
