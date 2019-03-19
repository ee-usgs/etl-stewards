package gov.acwi.wqp.etl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

public class EtlStewardsIT extends BaseFlowIT {

	@Test
//	@ExpectedDatabase(value="classpath:/testResult/wqp/orgData/allIndexes.xml",
//			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
//			table=BuildOrgDataIndexesFlowIT.EXPECTED_DATABASE_TABLE,
//			query=BuildOrgDataIndexesFlowIT.EXPECTED_DATABASE_QUERY)
//	@ExpectedDatabase(connection="pg", value="classpath:/testResult/wqp/orgData/create.xml",
//			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
//			table=SetupOrgDataSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
//			query=SetupOrgDataSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/wqp/orgData/orgData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void endToEndTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
