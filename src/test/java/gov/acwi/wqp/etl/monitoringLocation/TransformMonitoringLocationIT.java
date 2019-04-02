package gov.acwi.wqp.etl.monitoringLocation;

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
import gov.acwi.wqp.etl.monitoringLocation.index.BuildMonitoringLocationIndexesFlowIT;
import gov.acwi.wqp.etl.monitoringLocation.table.SetupMonitoringLocationSwapTableFlowIT;

public class TransformMonitoringLocationIT extends BaseArsFlowIT {

	@Autowired
	@Qualifier("monitoringLocationFlow")
	private Flow monitoringLocationFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Before
	public void setup() {
		testJob = jobBuilderFactory.get("monitoringLocationFlowTest")
				.start(monitoringLocationFlow)
				.build()
				.build();
		jobLauncherTestUtils.setJob(testJob);
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/monitoringLocation/empty.xml")
	@DatabaseSetup(connection="ars", value="classpath:/testData/ars/siteTypeToPrimary.xml")
	@DatabaseSetup(connection="ars", value="classpath:/testResult/ars/orgProject.xml")
	@DatabaseSetup(connection="ars", value="classpath:/testResult/ars/monitoringLocation.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformMonitoringLocationStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformMonitoringLocationStep", testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/stewards/monitoringLocation/empty.xml")
	@DatabaseSetup(connection="ars", value="classpath:/testData/ars/siteTypeToPrimary.xml")
	@DatabaseSetup(connection="ars", value="classpath:/testResult/ars/orgProject.xml")
	@DatabaseSetup(connection="ars", value="classpath:/testResult/ars/monitoringLocation.xml")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
				table=BuildMonitoringLocationIndexesFlowIT.EXPECTED_DATABASE_TABLE,
				query=BuildMonitoringLocationIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/monitoringLocation/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
				table=SetupMonitoringLocationSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
				query=SetupMonitoringLocationSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void monitoringLocationFlowTest() {
		Job monitoringLocationFlowTest = jobBuilderFactory.get("monitoringLocationFlowTest")
					.start(monitoringLocationFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(monitoringLocationFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
