package gov.acwi.wqp.etl.projectData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.BaseProcessorTest;
import gov.acwi.wqp.etl.orgData.OrgDataProcessorTest;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganization;

public class ProjectDataProcessorTest extends BaseProcessorTest {

	@Test
	public void happyPathTest() throws Exception {
		ArsOrganization arsOrganization = OrgDataProcessorTest.buildArsOrganization();

		ProjectDataProcessor processor = new ProjectDataProcessor();

		ProjectData projectData = processor.process(arsOrganization);
		assertEquals(Application.DATA_SOURCE_ID, projectData.getDataSourceId());
		assertEquals(Application.DATA_SOURCE, projectData.getDataSource());
		assertEquals(Application.ORGANIZATION_ID, projectData.getOrganizationId());
		assertEquals(TEST_ORG_ID, projectData.getOrganization());
		assertEquals(TEST_ORG_NAME, projectData.getOrganizationName());
		assertEquals(TEST_PROJECT_ID, projectData.getProjectIdentifier());
		assertEquals(TEST_PROJECT_NAME, projectData.getProjectName());
		assertEquals(TEST_PROJECT_DESCRIPTION, projectData.getDescription());
	}

	@Test
	public void nullTest() throws Exception {
		ArsOrganization arsOrganization = new ArsOrganization();

		ProjectDataProcessor processor = new ProjectDataProcessor();

		ProjectData projectData = processor.process(arsOrganization);
		assertEquals(Application.DATA_SOURCE_ID, projectData.getDataSourceId());
		assertEquals(Application.DATA_SOURCE, projectData.getDataSource());
		assertEquals(Application.ORGANIZATION_ID, projectData.getOrganizationId());
		assertNull(projectData.getOrganization());
		assertNull(projectData.getOrganizationName());
		assertNull(projectData.getProjectIdentifier());
		assertNull(projectData.getProjectName());
		assertNull(projectData.getDescription());
	}

}
