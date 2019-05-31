package gov.acwi.wqp.etl.resDetectQntLimit;

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

public class TransformResDetectQntLimitIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + "'r_detect_qnt_lmt_swap_stewards'";

	@Autowired
	@Qualifier("resDetectQntLimitFlow")
	private Flow resDetectQntLimitFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.ANALYZE_RES_DETECT_QNT_LIMIT_FLOW)
	private Flow analyzeResDetectQntLimitFlow;

	private Job setupFlowTestJob() {
		return jobBuilderFactory.get("resDetectQntLimitFlowTest").start(resDetectQntLimitFlow).build().build();
	}

	private Job setupAnalyzeTestJob() {
		return jobBuilderFactory.get("analyzeResDetectQntLimitFlowTest").start(analyzeResDetectQntLimitFlow).build().build();
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/resDetectQntLimit/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/result/result.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformResDetectQntLimitStepTest() {
		jobLauncherTestUtils.setJob(setupFlowTestJob());
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformResDetectQntLimitStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/resDetectQntLimit.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void analyzeResDetectQntLimitFlowTest() {
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
	@DatabaseSetup(value="classpath:/testData/stewards/resDetectQntLimit/resDetectQntLimitOld.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/activity.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/result/result.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/resDetectQntLimit/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'r_detect_qnt_lmt_swap_stewards'")
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/stewards/resDetectQntLimit/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'r_detect_qnt_lmt_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/analyze/resDetectQntLimit.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=TABLE_NAME_PG_STAT_ALL_TABLES,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)
	public void resDetectQntLimitFlowTest() {
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
