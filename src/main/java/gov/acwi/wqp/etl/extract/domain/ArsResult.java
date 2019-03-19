package gov.acwi.wqp.etl.extract.domain;

import org.postgis.PGgeometry;

public class ArsResult {

	private Integer stationId;
	private String siteId;
	private String organization;
	private String organizationName;
	private String monitoringLocationName;
	private String resolvedMonitoringLocationTypeName;
	private String hucTwelveDigitCode;
	private String governmentalUnitCode;
	private PGgeometry geom;
	private String monitoringLocationIdentifier;
	private Integer activityId;
	private String activityIdentifier;
	private String activityTypeCode;
	private String activityMediaName;
	private String activityStartDate;
	private String activityStartTime;
	private String activityStartTimeZoneCode;
	private String measureValue;
	private String measureUnitCode;
	private String activityDepthHeightMeasure;
	private String projectIdentifier;
	private String projectName;
	private String activityCommentText;
	private String sampleCollectionMethodIdentifier;
	private String sampleCollectionMethodIdentifierContext;
	private String sampleCollectionMethodName;
	private String sampleCollectionMethodDescriptionText;
	private String sampleCollectionEquipmentName;
	private String sampleCollectionEquipmentCommentText;
	private Integer resultId;
	private String resultDetectionConditionText;
	private String characteristicName;
	private String characteristicType;
	private String resultSampleFractionText;
	private String resultMeasureValue;
	private String resultMeasureUnitCode;
	private String resultStatusIdentifier;
	private String resultValueTypeName;
	private String dataQualityPrecisionValue;
	private String resultCommentText;
	private String resultAnalyticalMethodIdentifier;
	private String resultAnalyticalMethodIdentifierContext;
	private String resultAnalyticalMethodName;
	private String resultAnalyticalMethodDescriptionText;
	private String detectionQuantitationLimitTypeName;
	private String detectionQuantitationLimitMeasureValue;
	private String detectionQuantitationLimitMeasureUnitCode;

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
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getMonitoringLocationName() {
		return monitoringLocationName;
	}
	public void setMonitoringLocationName(String monitoringLocationName) {
		this.monitoringLocationName = monitoringLocationName;
	}
	public String getResolvedMonitoringLocationTypeName() {
		return resolvedMonitoringLocationTypeName;
	}
	public void setResolvedMonitoringLocationTypeName(String resolvedMonitoringLocationTypeName) {
		this.resolvedMonitoringLocationTypeName = resolvedMonitoringLocationTypeName;
	}
	public String getHucTwelveDigitCode() {
		return hucTwelveDigitCode;
	}
	public void setHucTwelveDigitCode(String hucTwelveDigitCode) {
		this.hucTwelveDigitCode = hucTwelveDigitCode;
	}
	public String getGovernmentalUnitCode() {
		return governmentalUnitCode;
	}
	public void setGovernmentalUnitCode(String governmentalUnitCode) {
		this.governmentalUnitCode = governmentalUnitCode;
	}
	public PGgeometry getGeom() {
		return geom;
	}
	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}
	public String getMonitoringLocationIdentifier() {
		return monitoringLocationIdentifier;
	}
	public void setMonitoringLocationIdentifier(String monitoringLocationIdentifier) {
		this.monitoringLocationIdentifier = monitoringLocationIdentifier;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getActivityIdentifier() {
		return activityIdentifier;
	}
	public void setActivityIdentifier(String activityIdentifier) {
		this.activityIdentifier = activityIdentifier;
	}
	public String getActivityTypeCode() {
		return activityTypeCode;
	}
	public void setActivityTypeCode(String activityTypeCode) {
		this.activityTypeCode = activityTypeCode;
	}
	public String getActivityMediaName() {
		return activityMediaName;
	}
	public void setActivityMediaName(String activityMediaName) {
		this.activityMediaName = activityMediaName;
	}
	public String getActivityStartDate() {
		return activityStartDate;
	}
	public void setActivityStartDate(String activityStartDate) {
		this.activityStartDate = activityStartDate;
	}
	public String getActivityStartTime() {
		return activityStartTime;
	}
	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}
	public String getActivityStartTimeZoneCode() {
		return activityStartTimeZoneCode;
	}
	public void setActivityStartTimeZoneCode(String activityStartTimeZoneCode) {
		this.activityStartTimeZoneCode = activityStartTimeZoneCode;
	}
	public String getMeasureValue() {
		return measureValue;
	}
	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}
	public String getMeasureUnitCode() {
		return measureUnitCode;
	}
	public void setMeasureUnitCode(String measureUnitCode) {
		this.measureUnitCode = measureUnitCode;
	}
	public String getActivityDepthHeightMeasure() {
		return activityDepthHeightMeasure;
	}
	public void setActivityDepthHeightMeasure(String activityDepthHeightMeasure) {
		this.activityDepthHeightMeasure = activityDepthHeightMeasure;
	}
	public String getProjectIdentifier() {
		return projectIdentifier;
	}
	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getActivityCommentText() {
		return activityCommentText;
	}
	public void setActivityCommentText(String activityCommentText) {
		this.activityCommentText = activityCommentText;
	}
	public String getSampleCollectionMethodIdentifier() {
		return sampleCollectionMethodIdentifier;
	}
	public void setSampleCollectionMethodIdentifier(String sampleCollectionMethodIdentifier) {
		this.sampleCollectionMethodIdentifier = sampleCollectionMethodIdentifier;
	}
	public String getSampleCollectionMethodIdentifierContext() {
		return sampleCollectionMethodIdentifierContext;
	}
	public void setSampleCollectionMethodIdentifierContext(String sampleCollectionMethodIdentifierContext) {
		this.sampleCollectionMethodIdentifierContext = sampleCollectionMethodIdentifierContext;
	}
	public String getSampleCollectionMethodName() {
		return sampleCollectionMethodName;
	}
	public void setSampleCollectionMethodName(String sampleCollectionMethodName) {
		this.sampleCollectionMethodName = sampleCollectionMethodName;
	}
	public String getSampleCollectionMethodDescriptionText() {
		return sampleCollectionMethodDescriptionText;
	}
	public void setSampleCollectionMethodDescriptionText(String sampleCollectionMethodDescriptionText) {
		this.sampleCollectionMethodDescriptionText = sampleCollectionMethodDescriptionText;
	}
	public String getSampleCollectionEquipmentName() {
		return sampleCollectionEquipmentName;
	}
	public void setSampleCollectionEquipmentName(String sampleCollectionEquipmentName) {
		this.sampleCollectionEquipmentName = sampleCollectionEquipmentName;
	}
	public String getSampleCollectionEquipmentCommentText() {
		return sampleCollectionEquipmentCommentText;
	}
	public void setSampleCollectionEquipmentCommentText(String sampleCollectionEquipmentCommentText) {
		this.sampleCollectionEquipmentCommentText = sampleCollectionEquipmentCommentText;
	}
	public Integer getResultId() {
		return resultId;
	}
	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}
	public String getResultDetectionConditionText() {
		return resultDetectionConditionText;
	}
	public void setResultDetectionConditionText(String resultDetectionConditionText) {
		this.resultDetectionConditionText = resultDetectionConditionText;
	}
	public String getCharacteristicName() {
		return characteristicName;
	}
	public void setCharacteristicName(String characteristicName) {
		this.characteristicName = characteristicName;
	}
	public String getCharacteristicType() {
		return characteristicType;
	}
	public void setCharacteristicType(String characteristicType) {
		this.characteristicType = characteristicType;
	}
	public String getResultSampleFractionText() {
		return resultSampleFractionText;
	}
	public void setResultSampleFractionText(String resultSampleFractionText) {
		this.resultSampleFractionText = resultSampleFractionText;
	}
	public String getResultMeasureValue() {
		return resultMeasureValue;
	}
	public void setResultMeasureValue(String resultMeasureValue) {
		this.resultMeasureValue = resultMeasureValue;
	}
	public String getResultMeasureUnitCode() {
		return resultMeasureUnitCode;
	}
	public void setResultMeasureUnitCode(String resultMeasureUnitCode) {
		this.resultMeasureUnitCode = resultMeasureUnitCode;
	}
	public String getResultStatusIdentifier() {
		return resultStatusIdentifier;
	}
	public void setResultStatusIdentifier(String resultStatusIdentifier) {
		this.resultStatusIdentifier = resultStatusIdentifier;
	}
	public String getResultValueTypeName() {
		return resultValueTypeName;
	}
	public void setResultValueTypeName(String resultValueTypeName) {
		this.resultValueTypeName = resultValueTypeName;
	}
	public String getDataQualityPrecisionValue() {
		return dataQualityPrecisionValue;
	}
	public void setDataQualityPrecisionValue(String dataQualityPrecisionValue) {
		this.dataQualityPrecisionValue = dataQualityPrecisionValue;
	}
	public String getResultCommentText() {
		return resultCommentText;
	}
	public void setResultCommentText(String resultCommentText) {
		this.resultCommentText = resultCommentText;
	}
	public String getResultAnalyticalMethodIdentifier() {
		return resultAnalyticalMethodIdentifier;
	}
	public void setResultAnalyticalMethodIdentifier(String resultAnalyticalMethodIdentifier) {
		this.resultAnalyticalMethodIdentifier = resultAnalyticalMethodIdentifier;
	}
	public String getResultAnalyticalMethodIdentifierContext() {
		return resultAnalyticalMethodIdentifierContext;
	}
	public void setResultAnalyticalMethodIdentifierContext(String resultAnalyticalMethodIdentifierContext) {
		this.resultAnalyticalMethodIdentifierContext = resultAnalyticalMethodIdentifierContext;
	}
	public String getResultAnalyticalMethodName() {
		return resultAnalyticalMethodName;
	}
	public void setResultAnalyticalMethodName(String resultAnalyticalMethodName) {
		this.resultAnalyticalMethodName = resultAnalyticalMethodName;
	}
	public String getResultAnalyticalMethodDescriptionText() {
		return resultAnalyticalMethodDescriptionText;
	}
	public void setResultAnalyticalMethodDescriptionText(String resultAnalyticalMethodDescriptionText) {
		this.resultAnalyticalMethodDescriptionText = resultAnalyticalMethodDescriptionText;
	}
	public String getDetectionQuantitationLimitTypeName() {
		return detectionQuantitationLimitTypeName;
	}
	public void setDetectionQuantitationLimitTypeName(String detectionQuantitationLimitTypeName) {
		this.detectionQuantitationLimitTypeName = detectionQuantitationLimitTypeName;
	}
	public String getDetectionQuantitationLimitMeasureValue() {
		return detectionQuantitationLimitMeasureValue;
	}
	public void setDetectionQuantitationLimitMeasureValue(String detectionQuantitationLimitMeasureValue) {
		this.detectionQuantitationLimitMeasureValue = detectionQuantitationLimitMeasureValue;
	}
	public String getDetectionQuantitationLimitMeasureUnitCode() {
		return detectionQuantitationLimitMeasureUnitCode;
	}
	public void setDetectionQuantitationLimitMeasureUnitCode(String detectionQuantitationLimitMeasureUnitCode) {
		this.detectionQuantitationLimitMeasureUnitCode = detectionQuantitationLimitMeasureUnitCode;
	}
}
