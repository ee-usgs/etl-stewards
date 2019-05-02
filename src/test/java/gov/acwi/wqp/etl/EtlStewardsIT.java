package gov.acwi.wqp.etl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.dbFinalize.UpdateLastETLIT;

public class EtlStewardsIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_TABLE_STATION_SUM = "station_sum_stewards";
	public static final String EXPECTED_DATABASE_QUERY_STATION_SUM = BASE_EXPECTED_DATABASE_QUERY_STATION_SUM + EXPECTED_DATABASE_TABLE_STATION_SUM;

	public static final String EXPECTED_DATABASE_QUERY_TABLE = BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE_LIKE
			+ "'%old' or table_name like '%stewards' or table_name like '%swap%'";
	public static final String EXPECTED_DATABASE_QUERY_INDEX = BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX_LIKE
			+ "'%old' or tablename like '%stewards' or tablename like '%swap%'";

	@Value("classpath:db/testInstall/setup.sql")
	protected Resource setupScript;

	@PostConstruct
	public void beforeThisClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(setupScript, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Test
	//Geospatial and lastEtl from wqp-etl-core
	@DatabaseSetup(connection=CONNECTION_NWIS, value="classpath:/testData/nwis/country/country.xml")
	@DatabaseSetup(connection=CONNECTION_NWIS, value="classpath:/testData/nwis/state/state.xml")
	@DatabaseSetup(connection=CONNECTION_NWIS, value="classpath:/testData/nwis/county/county.xml")
	@DatabaseSetup(value="classpath:/testData/wqp/lastEtl/lastEtl.xml")

	//Tables
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/wqp/installTables/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE, query=EXPECTED_DATABASE_QUERY_TABLE)

	//Indexes
	@ExpectedDatabase(connection=CONNECTION_INFORMATION_SCHEMA, value="classpath:/testResult/wqp/installIndexes/",
		assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
		table=EXPECTED_DATABASE_TABLE_CHECK_INDEX, query=EXPECTED_DATABASE_QUERY_INDEX)

	//Stewards Base Data
	@ExpectedDatabase(value="classpath:/testResult/wqp/orgData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/biologicalHabitatMetric.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/activityMetric.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/projectMLWeighting.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)

	//Summaries Data
	@ExpectedDatabase(value="classpath:/testResult/wqp/activitySum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/resultSum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/orgGrouping.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/mlGrouping.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
//TODO	@ExpectedDatabase(value="classpath:/testResult/stewards/organizationSum/organizationSum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/monitoringLocationSum.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_STATION_SUM,
			query=EXPECTED_DATABASE_QUERY_STATION_SUM)

	//Codes Data
	@ExpectedDatabase(value="classpath:/testResult/wqp/assemblage.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/characteristicName.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/characteristicType.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/country.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/county.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/monitoringLoc.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
//TODO	@ExpectedDatabase(value="classpath:/testResult/stewards/organization/organization.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/project.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/projectDim.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/sampleMedia.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/siteType.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/state.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/taxaName.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)

	@ExpectedDatabase(value="classpath:/testResult/wqp/lastEtl.xml",
		assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
		table=UpdateLastETLIT.TABLE_NAME_LAST_ETL, query=UpdateLastETLIT.EXPECTED_DATABASE_QUERY_LAST_ETL)

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
