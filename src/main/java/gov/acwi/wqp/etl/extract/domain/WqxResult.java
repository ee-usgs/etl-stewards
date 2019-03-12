package gov.acwi.wqp.etl.extract.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Result")
public class WqxResult {
	private WqxResultDescription resultDescription;
	private WqxResultAnalyticalMethod resultAnalyticalMethod;
	private WqxResultLabInformation resultLabInformation;

	public WqxResultDescription getResultDescription() {
		return resultDescription;
	}
	@XmlElement(name = "ResultDescription")
	public void setResultDescription(WqxResultDescription resultDescription) {
		this.resultDescription = resultDescription;
	}
	public WqxResultAnalyticalMethod getResultAnalyticalMethod() {
		return resultAnalyticalMethod;
	}
	@XmlElement(name = "ResultAnalyticalMethod")
	public void setResultAnalyticalMethod(WqxResultAnalyticalMethod resultAnalyticalMethod) {
		this.resultAnalyticalMethod = resultAnalyticalMethod;
	}
	public WqxResultLabInformation getResultLabInformation() {
		return resultLabInformation;
	}
	@XmlElement(name = "ResultLabInformation")
	public void setResultLabInformation(WqxResultLabInformation resultLabInformation) {
		this.resultLabInformation = resultLabInformation;
	}
}
