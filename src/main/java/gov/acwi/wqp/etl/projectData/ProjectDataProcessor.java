package gov.acwi.wqp.etl.projectData;

import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.extract.domain.ArsOrganization;
import gov.acwi.wqp.etl.projectData.ProjectData;

public class ProjectDataProcessor implements ItemProcessor<ArsOrganization, ProjectData>{

	@Override
	public ProjectData process(ArsOrganization arsOrganization) throws Exception {
		ProjectData projectData = new ProjectData();
		projectData.setDataSourceId(Application.DATA_SOURCE_ID);
		projectData.setDataSource(Application.DATA_SOURCE);
		projectData.setOrganizationId(Application.ORGANIZATION_ID);
		if (null != arsOrganization) {
			projectData.setOrganization(arsOrganization.getOrganizationIdentifier());
			projectData.setOrganizationName(arsOrganization.getOrganizationName());
			projectData.setProjectIdentifier(arsOrganization.getProjectIdentifier());
			projectData.setProjectName(arsOrganization.getProjectName());
			projectData.setDescription(arsOrganization.getProjectDescriptionText());
		}

		return projectData;
	}

}
