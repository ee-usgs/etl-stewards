package gov.acwi.wqp.etl.extract.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MonitoringLocationGeospatial")
public class WqxMonitoringLocationGeospatial {
	private String latitudeMeasure;
	private String longitudeMeasure;
	private String horizontalCollectionMethodName;
	private String horizontalCoordinateReferenceSystemDatumName;
	private String countryCode;
	private String stateCode;
	private String countyCode;
	public String getLatitudeMeasure() {
		return latitudeMeasure;
	}
	@XmlElement(name = "LatitudeMeasure")
	public void setLatitudeMeasure(String latitudeMeasure) {
		this.latitudeMeasure = latitudeMeasure;
	}
	public String getLongitudeMeasure() {
		return longitudeMeasure;
	}
	@XmlElement(name = "LongitudeMeasure")
	public void setLongitudeMeasure(String longitudeMeasure) {
		this.longitudeMeasure = longitudeMeasure;
	}
	public String getHorizontalCollectionMethodName() {
		return horizontalCollectionMethodName;
	}
	@XmlElement(name = "HorizontalCollectionMethodName")
	public void setHorizontalCollectionMethodName(String horizontalCollectionMethodName) {
		this.horizontalCollectionMethodName = horizontalCollectionMethodName;
	}
	public String getHorizontalCoordinateReferenceSystemDatumName() {
		return horizontalCoordinateReferenceSystemDatumName;
	}
	@XmlElement(name = "HorizontalCoordinateReferenceSystemDatumName")
	public void setHorizontalCoordinateReferenceSystemDatumName(String horizontalCoordinateReferenceSystemDatumName) {
		this.horizontalCoordinateReferenceSystemDatumName = horizontalCoordinateReferenceSystemDatumName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	@XmlElement(name = "CountryCode")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getStateCode() {
		return stateCode;
	}
	@XmlElement(name = "StateCode")
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getCountyCode() {
		return countyCode;
	}
	@XmlElement(name = "CountyCode")
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
}
