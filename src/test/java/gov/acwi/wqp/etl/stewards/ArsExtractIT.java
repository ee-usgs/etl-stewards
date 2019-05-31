package gov.acwi.wqp.etl.stewards;

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

public class ArsExtractIT extends ArsBaseFlowIT {

	@Autowired
	@Qualifier("arsExtractFlow")
	private Flow arsExtractFlow;

	@Test
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/orgProjectOld.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/monitoringLocationOld.xml")
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/resultOld.xml")
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsOrgProject/arsOrgProject.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsMonitoringLocation/arsMonitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/arsResult/arsResult.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsOrganizationPullFlowTest() {
		Job arsExtractFlowTest = jobBuilderFactory.get("arsOrganizationPullFlowTest")
					.start(arsExtractFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(arsExtractFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
}
