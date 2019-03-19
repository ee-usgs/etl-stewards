package gov.acwi.wqp.etl.monitoringLocation;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.extract.domain.ArsStation;

public class MonitoringLocationProcessor implements ItemProcessor<ArsStation, MonitoringLocation>{

	public static final String DEFAULT_SITE_TYPE = "Not Assigned";

	@Override
	public MonitoringLocation process(ArsStation arsStation) throws Exception {
		MonitoringLocation monitoringLocation = new MonitoringLocation();

		monitoringLocation.setDataSourceId(Application.DATA_SOURCE_ID);
		monitoringLocation.setDataSource(Application.DATA_SOURCE);
		monitoringLocation.setStationId(arsStation.getStationId());
		monitoringLocation.setSiteId(String.join("-", arsStation.getOrganization(), arsStation.getMonitoringLocationIdentifier()));
		monitoringLocation.setOrganization(arsStation.getOrganization());
		monitoringLocation.setSiteType(arsStation.getResolvedMonitoringLocationTypeName() == null ? DEFAULT_SITE_TYPE : arsStation.getResolvedMonitoringLocationTypeName());
		monitoringLocation.setHuc(arsStation.getHucTwelveDigitCode() == null ? arsStation.getHucEightDigitCode() : arsStation.getHucTwelveDigitCode());
		monitoringLocation.setGovernmentalUnitCode(String.join(":", arsStation.getCountryCode(), arsStation.getStateCode(), arsStation.getCountyCode()));
		monitoringLocation.setStationName(arsStation.getMonitoringLocationName());
		monitoringLocation.setOrganizationName(arsStation.getOrganizationName());
		monitoringLocation.setDescriptionText(arsStation.getMonitoringLocationDescriptionText());
		monitoringLocation.setStationTypeName(arsStation.getMonitoringLocationTypeName());
		monitoringLocation.setLatitude(getBigDecimal(arsStation.getLatitudeMeasure()));
		monitoringLocation.setLongitude(getBigDecimal(arsStation.getLongitudeMeasure()));
		monitoringLocation.setGeopositioningMethod(arsStation.getHorizontalCollectionMethodName());
		monitoringLocation.setHdatumIdCode(arsStation.getHorizontalCoordinateReferenceSystemDatumName());
		monitoringLocation.setDrainAreaValue(getBigDecimal(arsStation.getDrainageAreaMeasureValue()));
		monitoringLocation.setDrainAreaUnit(arsStation.getDrainageAreaMeasureUnitCode());
		monitoringLocation.calculateGeom(
				monitoringLocation.getLatitude(),
				monitoringLocation.getLongitude(),
				MonitoringLocation.DEFAULT_SRID);
		return monitoringLocation;
	}

	private BigDecimal getBigDecimal(String string) {
		if (NumberUtils.isCreatable(string)) {
			return NumberUtils.createBigDecimal(string);
		} else {
			return null;
		}
	}

}
