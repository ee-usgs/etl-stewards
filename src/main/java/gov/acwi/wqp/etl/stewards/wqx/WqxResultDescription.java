package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResultDescription")
public class WqxResultDescription {
	private String resultDetectionConditionText;
	private String characteristicName;
	private String resultSampleFractionText;
	private WqxResultMeasure resultMeasure;
	private String resultStatusIdentifier;
	private String resultValueTypeName;
	private WqxDataQuality dataQuality;
	private String resultCommentText;

	public String getResultDetectionConditionText() {
		return resultDetectionConditionText;
	}
	@XmlElement(name = "ResultDetectionConditionText")
	public void setResultDetectionConditionText(String resultDetectionConditionText) {
		this.resultDetectionConditionText = resultDetectionConditionText;
	}
	public String getCharacteristicName() {
		return characteristicName;
	}
	@XmlElement(name = "CharacteristicName")
	public void setCharacteristicName(String characteristicName) {
		this.characteristicName = characteristicName;
	}
	public String getResultSampleFractionText() {
		return resultSampleFractionText;
	}
	@XmlElement(name = "ResultSampleFractionText")
	public void setResultSampleFractionText(String resultSampleFractionText) {
		this.resultSampleFractionText = resultSampleFractionText;
	}
	public WqxResultMeasure getResultMeasure() {
		return resultMeasure;
	}
	@XmlElement(name = "ResultMeasure")
	public void setResultMeasure(WqxResultMeasure resultMeasure) {
		this.resultMeasure = resultMeasure;
	}
	public String getResultStatusIdentifier() {
		return resultStatusIdentifier;
	}
	@XmlElement(name = "ResultStatusIdentifier")
	public void setResultStatusIdentifier(String resultStatusIdentifier) {
		this.resultStatusIdentifier = resultStatusIdentifier;
	}
	public String getResultValueTypeName() {
		return resultValueTypeName;
	}
	@XmlElement(name = "ResultValueTypeName")
	public void setResultValueTypeName(String resultValueTypeName) {
		this.resultValueTypeName = resultValueTypeName;
	}
	public WqxDataQuality getDataQuality() {
		return dataQuality;
	}
	@XmlElement(name = "DataQuality")
	public void setDataQuality(WqxDataQuality dataQuality) {
		this.dataQuality = dataQuality;
	}
	public String getResultCommentText() {
		return resultCommentText;
	}
	@XmlElement(name = "ResultCommentText")
	public void setResultCommentText(String resultCommentText) {
		this.resultCommentText = resultCommentText;
	}
}
