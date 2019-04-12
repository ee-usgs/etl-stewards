package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MonitoringLocationIdentity")
public class WqxMonitoringLocationIdentity {

	private String monitoringLocationIdentifier;
	private String monitoringLocationName;
	private String monitoringLocationTypeName;
	private String monitoringLocationDescriptionText;
	private String hucEightDigitCode;
	private String hucTwelveDigitCode;
	private WqxDrainageAreaMeasure drainageAreaMeasure;

	public String getMonitoringLocationIdentifier() {
		return monitoringLocationIdentifier;
	}
	@XmlElement(name = "MonitoringLocationIdentifier")
	public void setMonitoringLocationIdentifier(String monitoringLocationIdentifier) {
		this.monitoringLocationIdentifier = monitoringLocationIdentifier;
	}
	public String getMonitoringLocationName() {
		return monitoringLocationName;
	}
	@XmlElement(name = "MonitoringLocationName")
	public void setMonitoringLocationName(String monitoringLocationName) {
		this.monitoringLocationName = monitoringLocationName;
	}
	public String getMonitoringLocationTypeName() {
		return monitoringLocationTypeName;
	}
	@XmlElement(name = "MonitoringLocationTypeName")
	public void setMonitoringLocationTypeName(String monitoringLocationTypeName) {
		this.monitoringLocationTypeName = monitoringLocationTypeName;
	}
	public String getMonitoringLocationDescriptionText() {
		return monitoringLocationDescriptionText;
	}
	@XmlElement(name = "MonitoringLocationDescriptionText")
	public void setMonitoringLocationDescriptionText(String monitoringLocationDescriptionText) {
		this.monitoringLocationDescriptionText = monitoringLocationDescriptionText;
	}
	public String getHucEightDigitCode() {
		return hucEightDigitCode;
	}
	@XmlElement(name = "HUCEightDigitCode")
	public void setHucEightDigitCode(String hucEightDigitCode) {
		this.hucEightDigitCode = hucEightDigitCode;
	}
	public String getHucTwelveDigitCode() {
		return hucTwelveDigitCode;
	}
	@XmlElement(name = "HUCTwelveDigitCode")
	public void setHucTwelveDigitCode(String hucTwelveDigitCode) {
		this.hucTwelveDigitCode = hucTwelveDigitCode;
	}
	public WqxDrainageAreaMeasure getDrainageAreaMeasure() {
		return drainageAreaMeasure;
	}
	@XmlElement(name = "DrainageAreaMeasure")
	public void setWqxDrainageAreaMeasure(WqxDrainageAreaMeasure drainageAreaMeasure) {
		this.drainageAreaMeasure = drainageAreaMeasure;
	}

}
