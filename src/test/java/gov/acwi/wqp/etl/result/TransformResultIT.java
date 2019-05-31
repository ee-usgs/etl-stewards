package gov.acwi.wqp.etl.result;

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

public class TransformResultIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + "'result_swap_stewards'";

	@Autowired
	@Qualifier("resultFlow")
	private Flow resultFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.ANALYZE_RESULT_FLOW)
	private Flow analyzeResultFlow;

	private Job setupFlowTestJob() {
		return jobBuilderFactory.get("resultFlowTest").start(resultFlow).build().build();
	}

	private Job setupAnalyzeTestJob() {
		return jobBuilderFactory.get("analyzeResultFlowTest").start(analyzeResultFlow).build().build();
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/result/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/activity.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsResult/arsResult.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/charNameToType.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformResultStepTest() {
		jobLauncherTestUtils.setJob(setupFlowTestJob());
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformResultStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/result.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void analyzeResultFlowTest() {
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
	@DatabaseSetup(value="classpath:/testData/stewards/result/resultOld.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/activity.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsResult/arsResult.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/charNameToType.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/stewards/result/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'result_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/result/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX
			, query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'result_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/result.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void resultFlowTest() {
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
