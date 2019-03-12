package gov.acwi.wqp.etl.monitoringLocation.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
import org.postgis.Point;
import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.extract.domain.ArsStation;

public class MonitoringLocationProcessor implements ItemProcessor<ArsStation, MonitoringLocation>{

	public static final int DEFAULT_SRID = 4269;
	public static final String DEFAULT_SITE_TYPE = "Not Assigned";

	@Override
	public MonitoringLocation process(ArsStation item) throws Exception {
		MonitoringLocation monitoringLocation = new MonitoringLocation();

		monitoringLocation.setDataSourceId(Application.DATA_SOURCE_ID);
		monitoringLocation.setDataSource(Application.DATA_SOURCE);
		monitoringLocation.setStationId(item.getStationId());
		monitoringLocation.setSiteId(String.join("-", item.getOrganization(), item.getMonitoringLocationIdentifier()));
		monitoringLocation.setOrganization(item.getOrganization());
		monitoringLocation.setSiteType(item.getResolvedMonitoringLocationTypeName() == null ? DEFAULT_SITE_TYPE : item.getResolvedMonitoringLocationTypeName());
		monitoringLocation.setHuc(item.getHucTwelveDigitCode() == null ? item.getHucEightDigitCode() : item.getHucTwelveDigitCode());
		monitoringLocation.setGovernmentalUnitCode(String.join(":", item.getCountryCode(), item.getStateCode(), item.getCountyCode()));
		monitoringLocation.setStationName(item.getMonitoringLocationName());
		monitoringLocation.setOrganizationName(item.getOrganizationName());
		monitoringLocation.setDescriptionText(item.getMonitoringLocationDescriptionText());
		monitoringLocation.setStationTypeName(item.getMonitoringLocationTypeName());
		monitoringLocation.setLatitude(getBigDecimal(item.getLatitudeMeasure()));
		monitoringLocation.setLongitude(getBigDecimal(item.getLongitudeMeasure()));
		monitoringLocation.setGeopositioningMethod(item.getHorizontalCollectionMethodName());
		monitoringLocation.setHdatumIdCode(item.getHorizontalCoordinateReferenceSystemDatumName());
		monitoringLocation.setDrainAreaValue(getBigDecimal(item.getDrainageAreaMeasureValue()));
		monitoringLocation.setDrainAreaUnit(item.getDrainageAreaMeasureUnitCode());
		monitoringLocation.setGeom(getPoint(monitoringLocation.getLatitude(), monitoringLocation.getLongitude()).toString()); //mdsys.sdo_geometry(2001,4269,mdsys.sdo_point_type(round(site.longitude, 7),round(site.latitude, 7), null), null, null) 
		return monitoringLocation;
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
