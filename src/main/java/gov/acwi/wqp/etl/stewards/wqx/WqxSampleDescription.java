package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SampleDescription")
public class WqxSampleDescription {
	private WqxCollectionMethod sampleCollectionMethod;
	private String sampleCollectionEquipmentName;
	private String sampleCollectionEquipmentCommentText;

	public WqxCollectionMethod getSampleCollectionMethod() {
		return sampleCollectionMethod;
	}
	@XmlElement(name = "SampleCollectionMethod")
	public void setSampleCollectionMethod(WqxCollectionMethod sampleCollectionMethod) {
		this.sampleCollectionMethod = sampleCollectionMethod;
	}
	public String getSampleCollectionEquipmentName() {
		return sampleCollectionEquipmentName;
	}
	@XmlElement(name = "SampleCollectionEquipmentName")
	public void setSampleCollectionEquipmentName(String sampleCollectionEquipmentName) {
		this.sampleCollectionEquipmentName = sampleCollectionEquipmentName;
	}
	public String getSampleCollectionEquipmentCommentText() {
		return sampleCollectionEquipmentCommentText;
	}
	@XmlElement(name = "SampleCollectionEquipmentCommentText")
	public void setSampleCollectionEquipmentCommentText(String sampleCollectionEquipmentCommentText) {
		this.sampleCollectionEquipmentCommentText = sampleCollectionEquipmentCommentText;
	}
}
