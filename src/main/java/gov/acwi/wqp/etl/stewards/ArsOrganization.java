package gov.acwi.wqp.etl.stewards;

import gov.acwi.wqp.etl.extract.domain.WqxOrganization;

public class ArsOrganization {

	public ArsOrganization() {}

	public ArsOrganization(WqxOrganization wqxOrganization) {
		if (null != wqxOrganization) {
			if (null != wqxOrganization.getOrganizationDescription()) {
				this.organizationIdentifier = wqxOrganization.getOrganizationDescription().getOrganizationIdentifier();
				this.organizationName = wqxOrganization.getOrganizationDescription().getOrganizationFormalName();
			}
			if (null != wqxOrganization.getProject()) {
				this.projectIdentifier = wqxOrganization.getProject().getProjectIdentifier();
				this.projectName = wqxOrganization.getProject().getProjectName();
				this.projectDescriptionText = wqxOrganization.getProject().getProjectDescriptionText();
			}
		}
	}

	private String organizationIdentifier;
	private String organizationName;
	private String projectIdentifier;
	private String projectName;
	private String projectDescriptionText;

	public String getOrganizationIdentifier() {
		return organizationIdentifier;
	}
	public void setOrganizationIdentifier(String organizationIdentifier) {
		this.organizationIdentifier = organizationIdentifier;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
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
	public String getProjectDescriptionText() {
		return projectDescriptionText;
	}
	public void setProjectDescriptionText(String projectDescriptionText) {
		this.projectDescriptionText = projectDescriptionText;
	}

}
