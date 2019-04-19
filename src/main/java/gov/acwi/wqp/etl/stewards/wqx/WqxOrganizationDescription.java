package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OrganizationDescription")
public class WqxOrganizationDescription {

	private String organizationIdentifier;
	private String organizationFormalName;

	public String getOrganizationIdentifier() {
		return organizationIdentifier;
	}
	@XmlElement(name = "OrganizationIdentifier")
	public void setOrganizationIdentifier(String organizationIdentifier) {
		this.organizationIdentifier = organizationIdentifier;
	}
	public String getOrganizationFormalName() {
		return organizationFormalName;
	}
	@XmlElement(name = "OrganizationFormalName")
	public void setOrganizationFormalName(String organizationFormalName) {
		this.organizationFormalName = organizationFormalName;
	}
}
