package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MonitoringLocation")
public class WqxMonitoringLocation {
	private WqxMonitoringLocationIdentity wqxMonitoringLocationIdentity;
	private WqxMonitoringLocationGeospatial wqxMonitoringLocationGeospatial;

	public WqxMonitoringLocationIdentity getWqxMonitoringLocationIdentity() {
		return wqxMonitoringLocationIdentity;
	}
	@XmlElement(name = "MonitoringLocationIdentity")
	public void setWqxMonitoringLocationIdentity(WqxMonitoringLocationIdentity wqxMonitoringLocationIdentity) {
		this.wqxMonitoringLocationIdentity = wqxMonitoringLocationIdentity;
	}
	public WqxMonitoringLocationGeospatial getWqxMonitoringLocationGeospatial() {
		return wqxMonitoringLocationGeospatial;
	}
	@XmlElement(name = "MonitoringLocationGeospatial")
	public void setWqxMonitoringLocationGeospatial(WqxMonitoringLocationGeospatial wqxMonitoringLocationGeospatial) {
		this.wqxMonitoringLocationGeospatial = wqxMonitoringLocationGeospatial;
	}
	
}
