package gov.acwi.wqp.etl.activity;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.stewards.ArsResult;

public class ActivityProcessor implements ItemProcessor<ArsResult, Activity>{

	@Override
	public Activity process(ArsResult item) throws Exception {
		Activity activity = new Activity();
		activity.setDataSourceId(Application.DATA_SOURCE_ID);
		activity.setDataSource(Application.DATA_SOURCE);
		activity.setStationId(item.getStationId());
		activity.setSiteId(item.getSiteId());
		activity.setEventDate(LocalDate.parse(item.getActivityStartDate()));
		activity.setActivity(item.getActivityIdentifier());
		activity.setSampleMedia(item.getActivityMediaName());
		activity.setOrganization(item.getOrganization());
		activity.setSiteType(item.getResolvedMonitoringLocationTypeName());
		activity.setHuc(item.getHucTwelveDigitCode());
		activity.setGovernmentalUnitCode(item.getGovernmentalUnitCode());
		activity.setGeom(item.getGeom()); 
		activity.setOrganizationName(item.getOrganizationName());
		activity.setActivityId(item.getActivityId());
		activity.setActivityTypeCode(item.getActivityTypeCode());
		activity.setActivityStartTime(item.getActivityStartTime());
		activity.setActStartTimeZone(item.getActivityStartTimeZoneCode());
		activity.setProjectId(item.getProjectIdentifier());
		activity.setProjectName(item.getProjectName());
		activity.setMonitoringLocationName(item.getMonitoringLocationName());
		activity.setSampleCollectMethodId(item.getSampleCollectionMethodIdentifier());
		activity.setSampleCollectMethodCtx(item.getSampleCollectionMethodIdentifierContext());
		activity.setSampleCollectMethodName(item.getSampleCollectionMethodName());
		activity.setSampleCollectEquipName(item.getSampleCollectionEquipmentName());
		return activity;
	}

}
