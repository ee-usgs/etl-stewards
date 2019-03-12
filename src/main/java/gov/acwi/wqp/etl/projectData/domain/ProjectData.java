package gov.acwi.wqp.etl.projectData.domain;

import gov.acwi.wqp.etl.extract.domain.ArsOrganization;
import gov.acwi.wqp.etl.orgData.domain.OrgData;

public class ProjectData extends OrgData {

	public ProjectData() {}

	public ProjectData(ArsOrganization organization) {
		super(organization);
		if (null != organization) {
			this.projectIdentifier = organization.getProjectIdentifier();
			this.projectName = organization.getProjectName();
			this.description = organization.getProjectDescriptionText();
		}
	}

	private String projectIdentifier;
	private String projectName;
	private String description;

	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
