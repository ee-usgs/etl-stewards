package gov.acwi.wqp.etl.projectData;

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

import gov.acwi.wqp.etl.BaseArsFlowIT;
import gov.acwi.wqp.etl.projectData.index.BuildProjectDataIndexesFlowIT;
import gov.acwi.wqp.etl.projectData.table.SetupProjectDataSwapTableFlowIT;

public class TransformProjectDataIT extends BaseArsFlowIT {

	@Autowired
	@Qualifier("projectDataFlow")
	private Flow projectDataFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Before
	public void setup() {
		super.baseSetup();
		testJob = jobBuilderFactory.get("projectDataFlow")
				.start(projectDataFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(testJob);
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/projectData/empty.xml")
	@DatabaseSetup(connection="ars", value="classpath:/testResult/ars/orgProject.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformProjectDataStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformProjectDataStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/stewards/projectData/projectDataOld.xml")
	@DatabaseSetup(connection="ars", value="classpath:/testResult/ars/orgProject.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectData/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildProjectDataIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildProjectDataIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/projectData/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupProjectDataSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupProjectDataSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void projectDataFlowTest() {
		Job projectDataFlowTest = jobBuilderFactory.get("projectDataFlowTest")
					.start(projectDataFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(projectDataFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
