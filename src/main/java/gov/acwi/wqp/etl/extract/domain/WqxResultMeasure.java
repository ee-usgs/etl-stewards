package gov.acwi.wqp.etl.extract.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResultMeasure")
public class WqxResultMeasure {
	private String resultMeasureValue;
	private String measureUnitCode;

	public String getResultMeasureValue() {
		return resultMeasureValue;
	}
	@XmlElement(name = "ResultMeasureValue")
	public void setResultMeasureValue(String resultMeasureValue) {
		this.resultMeasureValue = resultMeasureValue;
	}
	public String getMeasureUnitCode() {
		return measureUnitCode;
	}
	@XmlElement(name = "MeasureUnitCode")
	public void setMeasureUnitCode(String measureUnitCode) {
		this.measureUnitCode = measureUnitCode;
	}
}
