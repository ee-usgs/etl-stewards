package gov.acwi.wqp.etl.extract.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResultDetectionQuantitationLimit")
public class WqxResultDetectionQuantitationLimit {
	private String detectionQuantitationLimitTypeName;
	private WqxDetectionQuantitationLimitMeasure detectionQuantitationLimitMeasure;

	public String getDetectionQuantitationLimitTypeName() {
		return detectionQuantitationLimitTypeName;
	}
	@XmlElement(name = "DetectionQuantitationLimitTypeName")
	public void setDetectionQuantitationLimitTypeName(String detectionQuantitationLimitTypeName) {
		this.detectionQuantitationLimitTypeName = detectionQuantitationLimitTypeName;
	}
	public WqxDetectionQuantitationLimitMeasure getDetectionQuantitationLimitMeasure() {
		return detectionQuantitationLimitMeasure;
	}
	@XmlElement(name = "DetectionQuantitationLimitMeasure")
	public void setDetectionQuantitationLimitMeasure(WqxDetectionQuantitationLimitMeasure detectionQuantitationLimitMeasure) {
		this.detectionQuantitationLimitMeasure = detectionQuantitationLimitMeasure;
	}
}
