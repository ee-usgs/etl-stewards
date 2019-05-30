package gov.acwi.wqp.etl.stewards.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.ArsBaseFlowIT;

public class ArsResultPullIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + "'ars_result'";

	@Autowired
	@Qualifier("arsResultPullFlow")
	private Flow arsResultPullFlow;

	@Autowired
	@Qualifier("jdbcTemplateArs")
	private JdbcTemplate jdbcTemplateArs;

	@Test
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/resultOld.xml")
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsResult/empty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void truncateArsResultStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("truncateArsResultStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/ars/arsResult/indexes/drop.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'ars_result'")
	public void dropArsResultResultIdIndexStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("dropArsResultResultIdIndexStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsResult/arsResult.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsResultPullStepTest() {
		try {
			jdbcTemplateArs.execute("truncate table ars_result restart identity");
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("arsResultPullStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/ars/arsResult/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'ars_result'")
	public void buildArsResultResultIdIndexStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildArsResultResultIdIndexStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/ars/analyze/arsResult.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void analyzeArsResultTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("analyzeArsResultStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/resultOld.xml")
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsResult/arsResult.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/ars/analyze/arsResult.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	@ExpectedDatabase(value="classpath:/testResult/ars/arsResult/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'ars_result'")
	public void arsResultPullFlowTest() {
		Job arsResultPullFlowTest = jobBuilderFactory.get("arsResultPullFlowTest")
					.start(arsResultPullFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(arsResultPullFlowTest);
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
