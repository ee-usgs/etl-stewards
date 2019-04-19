package gov.acwi.wqp.etl.stewards.monitoringLocation;

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

public class ArsMonitoringLocationPullIT extends ArsBaseFlowIT {

	@Autowired
	@Qualifier("arsMonitoringLocationPullFlow")
	private Flow arsMonitoringLocationPullFlow;

	@Autowired
	@Qualifier("jdbcTemplateArs")
	private JdbcTemplate jdbcTemplateArs;

	@Test
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/monitoringLocationOld.xml")
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/monitoringLocationEmpty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void truncateArsMonitoringLocationStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("truncateArsMonitoringLocationStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsMonitoringLocationPullStepTest() {
		try {
			//Manually truncating to guarantee that the identity is reset. DBUnit @DatabaseSetup will not accomplish this.
			jdbcTemplateArs.execute("truncate table ars_monitoring_location restart identity");
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("arsMonitoringLocationPullStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(connection=CONNECTION_ARS, value="classpath:/testData/ars/monitoringLocationOld.xml")
	@ExpectedDatabase(connection=CONNECTION_ARS, value="classpath:/testResult/ars/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsMonitoringLocationPullFlowTest() {
		Job arsMonitoringLocationPullFlowTest = jobBuilderFactory.get("arsMonitoringLocationPullFlowTest")
					.start(arsMonitoringLocationPullFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(arsMonitoringLocationPullFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
