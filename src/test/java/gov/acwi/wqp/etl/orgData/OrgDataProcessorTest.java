package gov.acwi.wqp.etl.orgData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.BaseProcessorTest;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganization;

public class OrgDataProcessorTest extends BaseProcessorTest {

	@Test
	public void happyPathTest() throws Exception {
		ArsOrganization arsOrganization = buildArsOrganization();

		OrgDataProcessor processor = new OrgDataProcessor(configurationService);

		OrgData orgData = processor.process(arsOrganization);
		assertEquals(TEST_DATA_SOURCE_ID, orgData.getDataSourceId());
		assertEquals(TEST_DATA_SOURCE, orgData.getDataSource());
		assertEquals(Application.ORGANIZATION_ID, orgData.getOrganizationId());
		assertEquals(TEST_ORG_ID, orgData.getOrganization());
		assertEquals(TEST_ORG_NAME, orgData.getOrganizationName());
	}

	@Test
	public void nullTest() throws Exception {
		ArsOrganization arsOrganization = new ArsOrganization();

		OrgDataProcessor processor = new OrgDataProcessor(configurationService);

		OrgData orgData = processor.process(arsOrganization);
		assertEquals(TEST_DATA_SOURCE_ID, orgData.getDataSourceId());
		assertEquals(TEST_DATA_SOURCE, orgData.getDataSource());
		assertEquals(Application.ORGANIZATION_ID, orgData.getOrganizationId());
		assertNull(orgData.getOrganization());
		assertNull(orgData.getOrganizationName());
	}

	public static ArsOrganization buildArsOrganization() {
		ArsOrganization arsOrganization = new ArsOrganization();
		arsOrganization.setOrganizationIdentifier(TEST_ORG_ID);
		arsOrganization.setOrganizationName(TEST_ORG_NAME);
		arsOrganization.setProjectIdentifier(TEST_PROJECT_ID);
		arsOrganization.setProjectName(TEST_PROJECT_NAME);
		arsOrganization.setProjectDescriptionText(TEST_PROJECT_DESCRIPTION);
		return arsOrganization;
	}
}
