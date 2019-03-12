package gov.acwi.wqp.etl.extract.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Organization")
public class WqxOrganization {

	private WqxOrganizationDescription organizationDescription;
	private WqxProject project;

	public WqxOrganizationDescription getOrganizationDescription() {
		return organizationDescription;
	}
	@XmlElement(name = "OrganizationDescription")
	public void setOrganizationDescription(WqxOrganizationDescription organizationDescription) {
		this.organizationDescription = organizationDescription;
	}
	public WqxProject getProject() {
		return project;
	}
	@XmlElement(name = "Project")
	public void setProject(WqxProject project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "WqxOrganization [organizationDescription="
				+ (organizationDescription == null ? "{null}" : organizationDescription.toString())
				+ ", project="
				+ (project == null ? "{null}" : project.toString())
				+ "]";
	}

}
