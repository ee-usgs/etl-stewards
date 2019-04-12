package gov.acwi.wqp.etl.stewards.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import gov.acwi.wqp.etl.BaseProcessorTest;
import gov.acwi.wqp.etl.stewards.wqx.WqxOrganization;
import gov.acwi.wqp.etl.stewards.wqx.WqxOrganizationDescription;
import gov.acwi.wqp.etl.stewards.wqx.WqxProject;

public class ArsOrganizationProcessorTest extends BaseProcessorTest {

	@Test
	public void processHappyPathTest() throws Exception {
		WqxOrganization wqxOrganization = new WqxOrganization();
		wqxOrganization.setOrganizationDescription(buildWqxOrganizationDescription());
		wqxOrganization.setProject(buildWqxProject());

		ArsOrganizationProcessor processor = new ArsOrganizationProcessor();

		ArsOrganization arsOrganization = processor.process(wqxOrganization);

		assertEquals(TEST_ORG_ID, arsOrganization.getOrganizationIdentifier());
		assertEquals(TEST_ORG_NAME, arsOrganization.getOrganizationName());

		assertEquals(TEST_PROJECT_ID, arsOrganization.getProjectIdentifier());
		assertEquals(TEST_PROJECT_NAME, arsOrganization.getProjectName());
		assertEquals(TEST_PROJECT_DESCRIPTION, arsOrganization.getProjectDescriptionText());
	}

	@Test
	public void processNullOrganizationDescriptionTest() throws Exception {
		WqxOrganization wqxOrganization = new WqxOrganization();
		wqxOrganization.setProject(buildWqxProject());

		ArsOrganizationProcessor processor = new ArsOrganizationProcessor();

		ArsOrganization arsOrganization = processor.process(wqxOrganization);

		assertNull(arsOrganization.getOrganizationIdentifier());
		assertNull(arsOrganization.getOrganizationName());

		assertEquals(TEST_PROJECT_ID, arsOrganization.getProjectIdentifier());
		assertEquals(TEST_PROJECT_NAME, arsOrganization.getProjectName());
		assertEquals(TEST_PROJECT_DESCRIPTION, arsOrganization.getProjectDescriptionText());
	}

	@Test
	public void processNullProjectTest() throws Exception {
		WqxOrganization wqxOrganization = new WqxOrganization();
		wqxOrganization.setOrganizationDescription(buildWqxOrganizationDescription());

		ArsOrganizationProcessor processor = new ArsOrganizationProcessor();

		ArsOrganization arsOrganization = processor.process(wqxOrganization);

		assertEquals(TEST_ORG_ID, arsOrganization.getOrganizationIdentifier());
		assertEquals(TEST_ORG_NAME, arsOrganization.getOrganizationName());

		assertNull(arsOrganization.getProjectIdentifier());
		assertNull(arsOrganization.getProjectName());
		assertNull(arsOrganization.getProjectDescriptionText());
	}

	private WqxOrganizationDescription buildWqxOrganizationDescription() {
		WqxOrganizationDescription wqxOrganizationDescription = new WqxOrganizationDescription();
		wqxOrganizationDescription.setOrganizationIdentifier(SPACES.concat(TEST_ORG_ID).concat(SPACES));
		wqxOrganizationDescription.setOrganizationFormalName(SPACES.concat(TEST_ORG_NAME).concat(SPACES));
		return wqxOrganizationDescription;
	}

	private WqxProject buildWqxProject() {
		WqxProject wqxProject = new WqxProject();
		wqxProject.setProjectIdentifier(SPACES.concat(TEST_PROJECT_ID).concat(SPACES));
		wqxProject.setProjectName(SPACES.concat(TEST_PROJECT_NAME).concat(SPACES));
		wqxProject.setProjectDescriptionText(SPACES.concat(TEST_PROJECT_DESCRIPTION).concat(SPACES));
		return wqxProject;
	}
}
