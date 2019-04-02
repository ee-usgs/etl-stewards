package gov.acwi.wqp.etl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.codes.assemblage.index.BuildAssemblageIndexesFlowIT;
import gov.acwi.wqp.etl.codes.assemblage.table.SetupAssemblageSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.characteristicName.index.BuildCharacteristicNameIndexesFlowIT;
import gov.acwi.wqp.etl.codes.characteristicName.table.SetupCharacteristicNameSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.characteristicType.index.BuildCharacteristicTypeIndexesFlowIT;
import gov.acwi.wqp.etl.codes.characteristicType.table.SetupCharacteristicTypeSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.country.index.BuildCountryIndexesFlowIT;
import gov.acwi.wqp.etl.codes.country.table.SetupCountrySwapTableFlowIT;
import gov.acwi.wqp.etl.codes.county.index.BuildCountyIndexesFlowIT;
import gov.acwi.wqp.etl.codes.county.table.SetupCountySwapTableFlowIT;
import gov.acwi.wqp.etl.codes.monitoringLoc.index.BuildMonitoringLocIndexesFlowIT;
import gov.acwi.wqp.etl.codes.monitoringLoc.table.SetupMonitoringLocSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.organization.index.BuildOrganizationIndexesFlowIT;
import gov.acwi.wqp.etl.codes.organization.table.SetupOrganizationSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.project.index.BuildProjectIndexesFlowIT;
import gov.acwi.wqp.etl.codes.project.table.SetupProjectSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.projectDim.index.BuildProjectDimIndexesFlowIT;
import gov.acwi.wqp.etl.codes.projectDim.table.SetupProjectDimSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.sampleMedia.index.BuildSampleMediaIndexesFlowIT;
import gov.acwi.wqp.etl.codes.sampleMedia.table.SetupSampleMediaSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.siteType.index.BuildSiteTypeIndexesFlowIT;
import gov.acwi.wqp.etl.codes.siteType.table.SetupSiteTypeSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.state.index.BuildStateIndexesFlowIT;
import gov.acwi.wqp.etl.codes.state.table.SetupStateSwapTableFlowIT;
import gov.acwi.wqp.etl.codes.taxaName.index.BuildTaxaNameIndexesFlowIT;
import gov.acwi.wqp.etl.codes.taxaName.table.SetupTaxaNameSwapTableFlowIT;
import gov.acwi.wqp.etl.summaries.activitySum.index.BuildActivitySumIndexesFlowIT;
import gov.acwi.wqp.etl.summaries.activitySum.table.SetupActivitySumSwapTableFlowIT;
import gov.acwi.wqp.etl.summaries.mlGrouping.index.BuildMlGroupingIndexesFlowIT;
import gov.acwi.wqp.etl.summaries.mlGrouping.table.SetupMlGroupingSwapTableFlowIT;
import gov.acwi.wqp.etl.summaries.monitoringLocationSum.TransformMonitoringLocationSumIT;
import gov.acwi.wqp.etl.summaries.monitoringLocationSum.index.BuildMonitoringLocationSumIndexesFlowIT;
import gov.acwi.wqp.etl.summaries.monitoringLocationSum.table.SetupMonitoringLocationSumSwapTableFlowIT;
import gov.acwi.wqp.etl.summaries.orgGrouping.index.BuildOrgGroupingIndexesFlowIT;
import gov.acwi.wqp.etl.summaries.orgGrouping.table.SetupOrgGroupingSwapTableFlowIT;
import gov.acwi.wqp.etl.summaries.resultSum.index.BuildResultSumIndexesFlowIT;
import gov.acwi.wqp.etl.summaries.resultSum.table.SetupResultSumSwapTableFlowIT;

public class EtlStewardsIT extends BaseArsFlowIT {

	@Test
	//Stewards base results
	@ExpectedDatabase(value="classpath:/testResult/stewards/orgData/orgData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)

	//Summaries results
	@ExpectedDatabase(value="classpath:/testResult/stewards/activitySum/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildActivitySumIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildActivitySumIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/activitySum/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupActivitySumSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupActivitySumSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/resultSum/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildResultSumIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildResultSumIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/resultSum/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupResultSumSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupResultSumSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/orgGrouping/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildOrgGroupingIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildOrgGroupingIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/orgGrouping/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupOrgGroupingSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupOrgGroupingSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/mlGrouping/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildMlGroupingIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildMlGroupingIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/mlGrouping/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupMlGroupingSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupMlGroupingSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	//@ExpectedDatabase(value="classpath:/testResult/stewards/orgSum/indexes/all.xml",
	//	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	//	table=BuildOrgSumIndexesFlowIT.EXPECTED_DATABASE_TABLE,
	//	query=BuildOrgSumIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	//@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/orgSum/create.xml",
	//	assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
	//	table=SetupOrgSumSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
	//	query=SetupOrgSumSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocationSum/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildMonitoringLocationSumIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildMonitoringLocationSumIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/monitoringLocationSum/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupMonitoringLocationSumSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupMonitoringLocationSumSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	
	@ExpectedDatabase(value="classpath:/testResult/stewards/activitySum/activitySum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/resultSum/resultSum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/orgGrouping/orgGrouping.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/mlGrouping/mlGrouping.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	//TODO	@ExpectedDatabase(value="classpath:/testResult/stewards/orgSum/orgSum.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLocationSum/monitoringLocationSum.xml",
			table=TransformMonitoringLocationSumIT.EXPECTED_DATABASE_TABLE,
			query=TransformMonitoringLocationSumIT.EXPECTED_DATABASE_QUERY)

	//Codes results
	@ExpectedDatabase(value="classpath:/testResult/stewards/assemblage/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildAssemblageIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildAssemblageIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/assemblage/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupAssemblageSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupAssemblageSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/characteristicName/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildCharacteristicNameIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildCharacteristicNameIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/characteristicName/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupCharacteristicNameSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupCharacteristicNameSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/characteristicType/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildCharacteristicTypeIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildCharacteristicTypeIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/characteristicType/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupCharacteristicTypeSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupCharacteristicTypeSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/country/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildCountryIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildCountryIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/country/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupCountrySwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupCountrySwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/county/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildCountyIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildCountyIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/county/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupCountySwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupCountySwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLoc/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildMonitoringLocIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildMonitoringLocIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/monitoringLoc/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupMonitoringLocSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupMonitoringLocSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/organization/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildOrganizationIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildOrganizationIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/organization/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupOrganizationSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupOrganizationSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/project/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildProjectIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildProjectIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/project/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupProjectSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupProjectSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectDim/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildProjectDimIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildProjectDimIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/projectDim/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupProjectDimSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupProjectDimSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/sampleMedia/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildSampleMediaIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildSampleMediaIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/sampleMedia/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupSampleMediaSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupSampleMediaSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/siteType/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildSiteTypeIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildSiteTypeIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/siteType/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupSiteTypeSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupSiteTypeSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/state/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildStateIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildStateIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/state/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupStateSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupStateSwapTableFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(value="classpath:/testResult/stewards/taxaName/indexes/all.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=BuildTaxaNameIndexesFlowIT.EXPECTED_DATABASE_TABLE,
			query=BuildTaxaNameIndexesFlowIT.EXPECTED_DATABASE_QUERY)
	@ExpectedDatabase(connection="pg", value="classpath:/testResult/stewards/taxaName/create.xml",
			assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
			table=SetupTaxaNameSwapTableFlowIT.EXPECTED_DATABASE_TABLE,
			query=SetupTaxaNameSwapTableFlowIT.EXPECTED_DATABASE_QUERY)

	@ExpectedDatabase(value="classpath:/testResult/stewards/assemblage/assemblage.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/characteristicName/characteristicName.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/characteristicType/characteristicType.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/country/country.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/county/county.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/monitoringLoc/monitoringLoc.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/organization/organization.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/project/project.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/projectDim/projectDim.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/sampleMedia/sampleMedia.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/siteType/siteType.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/state/state.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	@ExpectedDatabase(value="classpath:/testResult/stewards/taxaName/taxaName.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
