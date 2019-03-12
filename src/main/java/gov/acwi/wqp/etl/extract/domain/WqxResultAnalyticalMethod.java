package gov.acwi.wqp.etl.extract.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResultAnalyticalMethod")
public class WqxResultAnalyticalMethod {
	private String MethodIdentifier;
	private String MethodIdentifierContext;
	private String MethodName;
	private String MethodDescriptionText;

	public String getMethodIdentifier() {
		return MethodIdentifier;
	}
	@XmlElement(name = "MethodIdentifier")
	public void setMethodIdentifier(String methodIdentifier) {
		MethodIdentifier = methodIdentifier;
	}
	public String getMethodIdentifierContext() {
		return MethodIdentifierContext;
	}
	@XmlElement(name = "MethodIdentifierContext")
	public void setMethodIdentifierContext(String methodIdentifierContext) {
		MethodIdentifierContext = methodIdentifierContext;
	}
	public String getMethodName() {
		return MethodName;
	}
	@XmlElement(name = "MethodName")
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	public String getMethodDescriptionText() {
		return MethodDescriptionText;
	}
	@XmlElement(name = "MethodDescriptionText")
	public void setMethodDescriptionText(String methodDescriptionText) {
		MethodDescriptionText = methodDescriptionText;
	}
}
