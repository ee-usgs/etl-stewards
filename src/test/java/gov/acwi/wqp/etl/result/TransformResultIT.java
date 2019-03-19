package gov.acwi.wqp.etl.result;

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
import gov.acwi.wqp.etl.result.index.BuildResultIndexesFlowIT;
import gov.acwi.wqp.etl.result.table.SetupResultSwapTableFlowIT;

public class TransformResultIT extends BaseFlowIT {

	@Autowired
	@Qualifier("resultFlow")
	private Flow resultFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Before
	public void setup() {
		testJob = jobBuilderFactory.get("resultFlowTest")
				.start(resultFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(testJob);
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/result/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/activity/activity.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsResult.xml")
	@DatabaseSetup(value="classpath:/testData/ars/arsCharNameToType.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
	@DatabaseSetup(value="classpath:/testResult/wqp/result/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/activity/activity.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsResult.xml")
	@DatabaseSetup(value="classpath:/testData/ars/arsCharNameToType.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/wqp/result/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupResultSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupResultSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildResultIndexesFlowIT.EXPECTED_DATABASE_TABLE
			, query=BuildResultIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
