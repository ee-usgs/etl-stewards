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

	@Autowired
	@Qualifier("arsResultPullFlow")
	private Flow arsResultPullFlow;

	@Autowired
	@Qualifier("jdbcTemplateArs")
	private JdbcTemplate jdbcTemplateArs;

	@Test
	@DatabaseSetup(connection="ars", value="classpath:/testData/ars/resultOld.xml")
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/resultEmpty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
	@DatabaseSetup(connection="ars", value="classpath:/testData/ars/resultOld.xml")
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsResultPullFlowTest() {
		Job arsResultPullFlowTest = jobBuilderFactory.get("arsResultPullFlowTest")
					.start(arsResultPullFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(arsResultPullFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
