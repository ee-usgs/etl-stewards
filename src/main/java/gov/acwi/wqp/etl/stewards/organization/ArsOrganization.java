package gov.acwi.wqp.etl.stewards.organization;

public class ArsOrganization {

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
