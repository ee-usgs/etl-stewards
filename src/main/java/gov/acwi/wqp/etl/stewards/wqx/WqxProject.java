package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Project")
public class WqxProject {

	private String projectIdentifier;
	private String projectName;
	private String projectDescriptionText;

	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	@XmlElement(name = "ProjectIdentifier")
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	public String getProjectName() {
		return projectName;
	}
	@XmlElement(name = "ProjectName")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescriptionText() {
		return projectDescriptionText;
	}
	@XmlElement(name = "ProjectDescriptionText")
	public void setProjectDescriptionText(String projectDescriptionText) {
		this.projectDescriptionText = projectDescriptionText;
	}
}
