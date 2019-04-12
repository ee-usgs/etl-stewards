package gov.acwi.wqp.etl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

public class EtlStewardsIT extends ArsBaseFlowIT {

	public static final String EXPECTED_DATABASE_TABLE_STATION_SUM = "station_sum_swap_stewards";
	public static final String EXPECTED_DATABASE_QUERY_STATION_SUM = BASE_EXPECTED_DATABASE_QUERY_STATION_SUM + EXPECTED_DATABASE_TABLE_STATION_SUM;

	@Test
	//Geospatial from wqp-etl-core
	@DatabaseSetup(connection="nwis", value="classpath:/testData/nwis/country/country.xml")
	@DatabaseSetup(connection="nwis", value="classpath:/testData/nwis/state/state.xml")
	@DatabaseSetup(connection="nwis", value="classpath:/testData/nwis/county/county.xml")

	//Stewards Base Tables
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/orgData/create.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'org_data_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/projectData/create.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'project_data_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/activity/create.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'activity_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/monitoringLocation/create.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
		table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
		query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'station_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/result/indexes/all.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_INDEX
	, query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'result_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/resDetectQntLimit/create.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'r_detect_qnt_lmt_swap_stewards'")

	//Stewards Base Data
	@ExpectedDatabase(value="classpath:/testResult/stewards/orgData/orgData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/monitoringLocation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)

	//Stewards Base Indexes
	@ExpectedDatabase(value="classpath:/testResult/stewards/orgData/indexes/all.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'org_data_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectData/indexes/all.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'project_data_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/indexes/all.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'activity_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocation/indexes/all.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
		table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
		query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'station_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/result/create.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'result_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/resDetectQntLimit/indexes/all.xml",
	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
	query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'r_detect_qnt_lmt_swap_stewards'")

	//Summaries Tables
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/activitySum/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'activity_sum_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/resultSum/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'result_sum_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/orgGrouping/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'org_grouping_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/mlGrouping/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'ml_grouping_swap_stewards'")
//TODO	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/organizationSum/create.xml",
//			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
//			table=EXPECTED_DATABASE_TABLE,
//			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/monitoringLocationSum/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'station_sum_swap_stewards'")

	//Summaries Data
	@ExpectedDatabase(value="classpath:/testResult/stewards/activitySum/activitySum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/resultSum/resultSum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/orgGrouping/orgGrouping.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/mlGrouping/mlGrouping.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
//TODO	@ExpectedDatabase(value="classpath:/testResult/stewards/organizationSum/organizationSum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocationSum/monitoringLocationSum.xml",
			table=EXPECTED_DATABASE_TABLE_STATION_SUM,
			query=EXPECTED_DATABASE_QUERY_STATION_SUM)

	//Summaries Indexes
	@ExpectedDatabase(value="classpath:/testResult/stewards/activitySum/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'activity_sum_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/resultSum/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'result_sum_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/orgGrouping/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'org_grouping_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/mlGrouping/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'ml_grouping_swap_stewards'")
//TODO	@ExpectedDatabase(value="classpath:/testResult/stewards/organizationSum/indexes/all.xml",
//			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
//			table=EXPECTED_DATABASE_TABLE,
//			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocationSum/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'station_sum_swap_stewards'")

	//Codes Tables
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/assemblage/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'assemblage_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/characteristicName/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'char_name_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/characteristicType/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'char_type_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/country/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'country_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/county/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'county_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/monitoringLoc/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'monitoring_loc_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/organization/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'organization_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/project/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'project_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/projectDim/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'project_dim_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/sampleMedia/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'sample_media_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/siteType/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'site_type_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/state/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'state_swap_stewards'")
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/taxaName/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + "'taxa_name_swap_stewards'")

	//Codes Data
	@ExpectedDatabase(value="classpath:/testResult/stewards/assemblage/assemblage.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/characteristicName/characteristicName.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/characteristicType/characteristicType.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/country/country.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/county/county.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLoc/monitoringLoc.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
//TODO	@ExpectedDatabase(value="classpath:/testResult/stewards/organization/organization.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/project/project.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectDim/projectDim.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/sampleMedia/sampleMedia.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/siteType/siteType.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/state/state.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/taxaName/taxaName.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)

	//Codes Indexes
	@ExpectedDatabase(value="classpath:/testResult/stewards/assemblage/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'assemblage_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/characteristicName/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'char_name_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/characteristicType/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'char_type_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/country/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'country_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/county/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'county_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLoc/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'monitoring_loc_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/organization/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'organization_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/project/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'project_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectDim/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'project_dim_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/sampleMedia/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'sample_media_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/siteType/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'site_type_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/state/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'state_swap_stewards'")
	@ExpectedDatabase(value="classpath:/testResult/stewards/taxaName/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
			query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + "'taxa_name_swap_stewards'")

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
