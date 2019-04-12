package gov.acwi.wqp.etl.stewards.organization;

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

public class ArsOrganizationPullIT extends ArsBaseFlowIT {

	@Autowired
	@Qualifier("arsOrganizationPullFlow")
	private Flow arsOrganizationPullFlow;

	@Test
	@DatabaseSetup(connection="ars", value="classpath:/testData/ars/orgProjectOld.xml")
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/orgProjectEmpty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void truncateArsOrgProjectStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("truncateArsOrgProjectStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(connection="ars", value="classpath:/testResult/ars/orgProjectEmpty.xml")
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/orgProject.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsOrganizationPullStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("arsOrganizationPullStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(connection="ars", value="classpath:/testData/ars/orgProjectOld.xml")
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/orgProject.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsOrganizationPullFlowTest() {
		Job arsOrganizationPullFlowTest = jobBuilderFactory.get("arsOrganizationPullFlowTest")
					.start(arsOrganizationPullFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(arsOrganizationPullFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
