package gov.acwi.wqp.etl.activity.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.commons.lang3.math.NumberUtils;
import org.postgis.Point;
import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.extract.domain.ArsResult;

public class ActivityProcessor implements ItemProcessor<ArsResult, Activity>{

	public static final int DEFAULT_SRID = 4269;

	@Override
	public Activity process(ArsResult item) throws Exception {
		Activity activity = new Activity();

		activity.setDataSourceId(Application.DATA_SOURCE_ID);
		activity.setDataSource(Application.DATA_SOURCE);
		activity.setStationId(item.getStationId());
		activity.setSiteId(String.join("-", item.getOrganization(), item.getMonitoringLocationIdentifier()));
		activity.setEventDate(LocalDate.parse(item.getActivityStartDate()));
		activity.setActivity(item.getActivityIdentifier());
		activity.setSampleMedia(item.getActivityMediaName());
		activity.setOrganization(item.getOrganization());
		activity.setSiteType(item.getSiteType());
		activity.setHuc(item.getHuc());
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

	private BigDecimal getBigDecimal(String string) {
		if (NumberUtils.isCreatable(string)) {
			return NumberUtils.createBigDecimal(string);
		} else {
			return null;
		}
	}

	private Point getPoint(BigDecimal latitude, BigDecimal longitude) {
		Point point = null;
		if (null != latitude && null != longitude) {
			point = new Point(longitude.doubleValue(), latitude.doubleValue());
			point.setSrid(DEFAULT_SRID);
		}
		return point;
	}
}
