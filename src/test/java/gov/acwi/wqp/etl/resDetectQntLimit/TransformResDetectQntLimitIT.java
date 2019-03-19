package gov.acwi.wqp.etl.resDetectQntLimit;

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
import gov.acwi.wqp.etl.resDetectQntLimit.index.BuildResDetectQntLimitIndexesFlowIT;
import gov.acwi.wqp.etl.resDetectQntLimit.table.SetupResDetectQntLimitSwapTableFlowIT;

public class TransformResDetectQntLimitIT extends BaseFlowIT {

	@Autowired
	@Qualifier("resDetectQntLimitFlow")
	private Flow resDetectQntLimitFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Before
	public void setup() {
		testJob = jobBuilderFactory.get("resultFlowTest")
				.start(resDetectQntLimitFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(testJob);
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/resDetectQntLimit/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/result/result.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformResDetectQntLimitStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformResDetectQntLimitStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/resDetectQntLimit/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/activity/activity.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/result/result.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildResDetectQntLimitIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildResDetectQntLimitIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/wqp/resDetectQntLimit/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupResDetectQntLimitSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupResDetectQntLimitSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void resDetectQntLimitFlowTest() {
		Job resDetectQntLimitFlowTest = jobBuilderFactory.get("resDetectQntLimitFlowTest")
					.start(resDetectQntLimitFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(resDetectQntLimitFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
