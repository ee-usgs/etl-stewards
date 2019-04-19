package gov.acwi.wqp.etl.stewards.wqx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ActivityDescription")
public class WqxActivityDescription {
	private String activityIdentifier;
	private String activityTypeCode;
	private String activityMediaName;
	private String activityStartDate;
	private WqxTime activityStartTime;
	private String projectIdentifier;
	private String monitoringLocationIdentifier;
	private String activityCommentText;

	public String getActivityIdentifier() {
		return activityIdentifier;
	}
	@XmlElement(name = "ActivityIdentifier")
	public void setActivityIdentifier(String activityIdentifier) {
		this.activityIdentifier = activityIdentifier;
	}
	public String getActivityTypeCode() {
		return activityTypeCode;
	}
	@XmlElement(name = "ActivityTypeCode")
	public void setActivityTypeCode(String activityTypeCode) {
		this.activityTypeCode = activityTypeCode;
	}
	public String getActivityMediaName() {
		return activityMediaName;
	}
	@XmlElement(name = "ActivityMediaName")
	public void setActivityMediaName(String activityMediaName) {
		this.activityMediaName = activityMediaName;
	}
	public String getActivityStartDate() {
		return activityStartDate;
	}
	@XmlElement(name = "ActivityStartDate")
	public void setActivityStartDate(String activityStartDate) {
		this.activityStartDate = activityStartDate;
	}
	public WqxTime getActivityStartTime() {
		return activityStartTime;
	}
	@XmlElement(name = "ActivityStartTime")
	public void setActivityStartTime(WqxTime activityStartTime) {
		this.activityStartTime = activityStartTime;
	}
	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	@XmlElement(name = "ProjectIdentifier")
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	public String getMonitoringLocationIdentifier() {
		return monitoringLocationIdentifier;
	}
	@XmlElement(name = "MonitoringLocationIdentifier")
	public void setMonitoringLocationIdentifier(String monitoringLocationIdentifier) {
		this.monitoringLocationIdentifier = monitoringLocationIdentifier;
	}
	public String getActivityCommentText() {
		return activityCommentText;
	}
	@XmlElement(name = "ActivityCommentText")
	public void setActivityCommentText(String activityCommentText) {
		this.activityCommentText = activityCommentText;
	}
}
