package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CollectionMethod")
public class WqxCollectionMethod {
	private String methodIdentifier;
	private String methodIdentifierContext;
	private String methodName;
	private String methodDescriptionText;

	public String getMethodIdentifier() {
		return methodIdentifier;
	}
	@XmlElement(name = "MethodIdentifier")
	public void setMethodIdentifier(String methodIdentifier) {
		this.methodIdentifier = methodIdentifier;
	}
	public String getMethodIdentifierContext() {
		return methodIdentifierContext;
	}
	@XmlElement(name = "MethodIdentifierContext")
	public void setMethodIdentifierContext(String methodIdentifierContext) {
		this.methodIdentifierContext = methodIdentifierContext;
	}
	public String getMethodName() {
		return methodName;
	}
	@XmlElement(name = "MethodName")
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodDescriptionText() {
		return methodDescriptionText;
	}
	@XmlElement(name = "MethodDescriptionText")
	public void setMethodDescriptionText(String methodDescriptionText) {
		this.methodDescriptionText = methodDescriptionText;
	}
}
