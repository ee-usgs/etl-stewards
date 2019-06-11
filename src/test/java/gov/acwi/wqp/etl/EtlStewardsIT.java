package gov.acwi.wqp.etl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.dbFinalize.UpdateLastETLIT;

public class EtlStewardsIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_TABLE_STATION_SUM = "station_sum_stewards";
	public static final String EXPECTED_DATABASE_QUERY_STATION_SUM = BASE_EXPECTED_DATABASE_QUERY_STATION_SUM + EXPECTED_DATABASE_TABLE_STATION_SUM;

	public static final String EXPECTED_DATABASE_QUERY_TABLE = BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE_LIKE
			+ "'%stewards%' and table_name not like '%swap%'";
	public static final String EXPECTED_DATABASE_QUERY_INDEX = BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX_LIKE
			+ "'%stewards' and tablename not like '%swap%'";

	public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE_BARE
			+ "where relname like '%_stewards' and relname not like '%swap%' and relname not like '%object%'";

	public static final String EXPECTED_DATABASE_QUERY_PRIMARY_KEY = BASE_EXPECTED_DATABASE_QUERY_PRIMARY_KEY
			+ " like '%_stewards'";

	public static final String EXPECTED_DATABASE_QUERY_FOREIGN_KEY = BASE_EXPECTED_DATABASE_QUERY_FOREIGN_KEY
			+ " like '%_stewards'";

	@Test
	//Geospatial and lastEtl from wqp-etl-core
	@DatabaseSetup(connection=CONNECTION_NWIS, value="classpath:/testData/nwis/country/country.xml")
	@DatabaseSetup(connection=CONNECTION_NWIS, value="classpath:/testData/nwis/state/state.xml")
	@DatabaseSetup(connection=CONNECTION_NWIS, value="classpath:/testData/nwis/county/county.xml")
	@DatabaseSetup(value="classpath:/testData/wqp/lastEtl/lastEtl.xml")

	//Tables
	@ExpectedDatabase(
			connection=CONNECTION_INFORMATION_SCHEMA,
			value="classpath:/testResult/wqp/installTables/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=EXPECTED_DATABASE_QUERY_TABLE)

	//Indexes
	@ExpectedDatabase(
			connection=CONNECTION_INFORMATION_SCHEMA,
			value="classpath:/testResult/wqp/installIndexes/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=EXPECTED_DATABASE_QUERY_INDEX)

	//Analyzed
	@ExpectedDatabase(
			value="classpath:/testResult/wqp/installAnalyzed/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_ANALYZE,
			query=EXPECTED_DATABASE_QUERY_ANALYZE)

	//Primary Keys
	@ExpectedDatabase(
			value="classpath:/testResult/wqp/primaryKey/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_PRIMARY_KEY,
			query=EXPECTED_DATABASE_QUERY_PRIMARY_KEY)

	//Foreign Keys
	@ExpectedDatabase(
			value="classpath:/testResult/wqp/foreignKey/",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_FOREIGN_KEY,
			query=EXPECTED_DATABASE_QUERY_FOREIGN_KEY)

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
	@ExpectedDatabase(value="classpath:/testResult/wqp/organizationSum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(
			value="classpath:/testResult/wqp/monitoringLocationSum.xml",
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
	@ExpectedDatabase(value="classpath:/testResult/wqp/organization.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/project.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/projectDim.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/sampleMedia.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/siteType.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/state.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/wqp/taxaName.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)

	@ExpectedDatabase(
			value="classpath:/testResult/wqp/lastEtl.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=UpdateLastETLIT.TABLE_NAME_LAST_ETL,
			query=UpdateLastETLIT.EXPECTED_DATABASE_QUERY_LAST_ETL)

	public void endToEndTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
