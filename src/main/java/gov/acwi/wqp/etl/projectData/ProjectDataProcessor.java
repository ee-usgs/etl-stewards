package gov.acwi.wqp.etl.projectData;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.ConfigurationService;
import gov.acwi.wqp.etl.projectData.ProjectData;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganization;

@Component
public class ProjectDataProcessor implements ItemProcessor<ArsOrganization, ProjectData> {

	private final ConfigurationService configurationService;
	private static final int DEFAULT_PROJECT_ID = 1;

	@Autowired
	public ProjectDataProcessor(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@Override
	public ProjectData process(ArsOrganization arsOrganization) throws Exception {
		ProjectData projectData = new ProjectData();
		projectData.setDataSourceId(configurationService.getEtlDataSourceId());
		projectData.setDataSource(configurationService.getEtlDataSource());
		projectData.setOrganizationId(Application.ORGANIZATION_ID);
		projectData.setProjectId(DEFAULT_PROJECT_ID);
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
