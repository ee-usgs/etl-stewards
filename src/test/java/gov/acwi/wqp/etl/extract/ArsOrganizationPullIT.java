package gov.acwi.wqp.etl.extract;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.BaseFlowIT;

public class ArsOrganizationPullIT extends BaseFlowIT {

	@Autowired
	@Qualifier("arsOrganizationPullFlow")
	private Flow arsOrganizationPullFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Before
	public void setup() {
		testJob = jobBuilderFactory.get("arsOrganizationPullFlowest")
				.start(arsOrganizationPullFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(testJob);
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/ars/arsOrgProjectOld.xml")
	@ExpectedDatabase(value="classpath:/testResult/ars/arsOrgProjectEmpty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
	@ExpectedDatabase(value="classpath:/testResult/ars/arsOrgProject.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsOrganizationPullStepTest() {
		try {
			jdbcTemplate.execute("truncate table ars_org_project");
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("arsOrganizationPullStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/ars/arsOrgProjectOld.xml")
	@ExpectedDatabase(value="classpath:/testResult/ars/arsOrgProject.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
