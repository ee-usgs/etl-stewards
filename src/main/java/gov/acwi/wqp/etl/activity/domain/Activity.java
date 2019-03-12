package gov.acwi.wqp.etl.activity.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Activity {

	private Integer dataSourceId;
	private String dataSource;
	private Integer stationId;
	private String siteId;
	private LocalDate eventDate;
	private String activity;
	private String sampleMedia;
	private String organization;
	private String siteType;
	private String huc;
	private String governmentalUnitCode;
	private String geom;
	private String organizationName;
	private Integer activityId;
	private String activityTypeCode;
	private String activityStartTime;
	private String actStartTimeZone;
	private String projectId;
	private String projectName;
	private String monitoringLocationName;
	private String sampleCollectMethodId;
	private String sampleCollectMethodCtx;
	private String sampleCollectMethodName;
	private String sampleCollectEquipName;

	public Integer getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getSampleMedia() {
		return sampleMedia;
	}
	public void setSampleMedia(String sampleMedia) {
		this.sampleMedia = sampleMedia;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getSiteType() {
		return siteType;
	}
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	public String getHuc() {
		return huc;
	}
	public void setHuc(String huc) {
		this.huc = huc;
	}
	public String getGovernmentalUnitCode() {
		return governmentalUnitCode;
	}
	public void setGovernmentalUnitCode(String governmentalUnitCode) {
		this.governmentalUnitCode = governmentalUnitCode;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getActivityTypeCode() {
		return activityTypeCode;
	}
	public void setActivityTypeCode(String activityTypeCode) {
		this.activityTypeCode = activityTypeCode;
	}
	public String getActivityStartTime() {
		return activityStartTime;
	}
	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}
	public String getActStartTimeZone() {
		return actStartTimeZone;
	}
	public void setActStartTimeZone(String actStartTimeZone) {
		this.actStartTimeZone = actStartTimeZone;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getMonitoringLocationName() {
		return monitoringLocationName;
	}
	public void setMonitoringLocationName(String monitoringLocationName) {
		this.monitoringLocationName = monitoringLocationName;
	}
	public String getSampleCollectMethodId() {
		return sampleCollectMethodId;
	}
	public void setSampleCollectMethodId(String sampleCollectMethodId) {
		this.sampleCollectMethodId = sampleCollectMethodId;
	}
	public String getSampleCollectMethodCtx() {
		return sampleCollectMethodCtx;
	}
	public void setSampleCollectMethodCtx(String sampleCollectMethodCtx) {
		this.sampleCollectMethodCtx = sampleCollectMethodCtx;
	}
	public String getSampleCollectMethodName() {
		return sampleCollectMethodName;
	}
	public void setSampleCollectMethodName(String sampleCollectMethodName) {
		this.sampleCollectMethodName = sampleCollectMethodName;
	}
	public String getSampleCollectEquipName() {
		return sampleCollectEquipName;
	}
	public void setSampleCollectEquipName(String sampleCollectEquipName) {
		this.sampleCollectEquipName = sampleCollectEquipName;
	}
}
