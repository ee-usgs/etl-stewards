package gov.acwi.wqp.etl.activity;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.stewards.result.ArsResult;

public class ActivityProcessor implements ItemProcessor<ArsResult, Activity>{

	@Override
	public Activity process(ArsResult arsResult) throws Exception {
		Activity activity = new Activity();
		activity.setDataSourceId(Application.DATA_SOURCE_ID);
		activity.setDataSource(Application.DATA_SOURCE);
		activity.setStationId(arsResult.getStationId());
		activity.setSiteId(arsResult.getSiteId());
		activity.setEventDate(LocalDate.parse(arsResult.getActivityStartDate(), Application.ARS_DATE_TIME_FORMATTER));
		activity.setActivity(arsResult.getActivityIdentifier());
		activity.setSampleMedia(arsResult.getActivityMediaName());
		activity.setOrganization(arsResult.getOrganization());
		activity.setSiteType(arsResult.getResolvedMonitoringLocationTypeName());
		activity.setHuc(arsResult.getHucTwelveDigitCode());
		activity.setGovernmentalUnitCode(arsResult.getGovernmentalUnitCode());
		activity.setGeom(arsResult.getGeom()); 
		activity.setOrganizationName(arsResult.getOrganizationName());
		activity.setActivityId(arsResult.getActivityId());
		activity.setActivityTypeCode(arsResult.getActivityTypeCode());
		activity.setActivityStartTime(arsResult.getActivityStartTime());
		activity.setActStartTimeZone(arsResult.getActivityStartTimeZoneCode());
		activity.setProjectId(arsResult.getProjectIdentifier());
		activity.setProjectName(arsResult.getProjectName());
		activity.setMonitoringLocationName(arsResult.getMonitoringLocationName());
		activity.setActivityComment(arsResult.getActivityCommentText());
		activity.setSampleCollectMethodId(arsResult.getSampleCollectionMethodIdentifier());
		activity.setSampleCollectMethodCtx(arsResult.getSampleCollectionMethodIdentifierContext());
		activity.setSampleCollectMethodName(arsResult.getSampleCollectionMethodName());
		activity.setSampleCollectEquipName(arsResult.getSampleCollectionEquipmentName());
		return activity;
	}

}
