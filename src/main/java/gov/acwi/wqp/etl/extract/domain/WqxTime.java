package gov.acwi.wqp.etl.extract.domain;

import javax.xml.bind.annotation.XmlElement;

public class WqxTime {
	private String time;
	private String timeZoneCode;

	public String getTime() {
		return time;
	}
	@XmlElement(name = "Time")
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimeZoneCode() {
		return timeZoneCode;
	}
	@XmlElement(name = "TimeZoneCode")
	public void setTimeZoneCode(String timeZoneCode) {
		this.timeZoneCode = timeZoneCode;
	}
}
