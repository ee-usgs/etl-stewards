package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResultMeasure")
public class WqxDetectionQuantitationLimitMeasure {
	private String measureValue;
	private String measureUnitCode;

	public String getMeasureValue() {
		return measureValue;
	}
	@XmlElement(name = "MeasureValue")
	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}
	public String getMeasureUnitCode() {
		return measureUnitCode;
	}
	@XmlElement(name = "MeasureUnitCode")
	public void setMeasureUnitCode(String measureUnitCode) {
		this.measureUnitCode = measureUnitCode;
	}

}
