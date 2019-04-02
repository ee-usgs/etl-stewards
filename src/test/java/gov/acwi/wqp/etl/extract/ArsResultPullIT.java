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

public class ArsResultPullIT extends BaseFlowIT {

	@Autowired
	@Qualifier("arsResultPullFlow")
	private Flow arsResultPullFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Before
	public void setup() {
		testJob = jobBuilderFactory.get("arsResultPullFlowTest")
				.start(arsResultPullFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(testJob);
	}

	@Test
	@DatabaseSetup(connection="ars", value="classpath:/testData/ars/arsResultOld.xml")
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/arsResultEmpty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/arsResult.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void arsResultPullStepTest() {
		try {
			jdbcTemplate.execute("truncate table ars_result restart identity");
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("arsResultPullStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(connection="ars", value="classpath:/testData/ars/arsResultOld.xml")
	@ExpectedDatabase(connection="ars", value="classpath:/testResult/ars/arsResult.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
