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

public class TransformResultIT extends ArsBaseFlowIT {

	@Autowired
	@Qualifier("resultFlow")
	private Flow resultFlow;

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/result/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/activity.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/result.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/charNameToType.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformResultStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformResultStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/stewards/result/resultOld.xml")
	@DatabaseSetup(value="classpath:/testResult/stewards/activity/activity.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testResult/ars/result.xml")
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
	public void resultFlowTest() {
		Job resultFlowTest = jobBuilderFactory.get("resultFlowTest")
					.start(resultFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(resultFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
