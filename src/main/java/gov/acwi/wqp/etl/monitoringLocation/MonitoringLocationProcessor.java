package gov.acwi.wqp.etl.monitoringLocation;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.acwi.wqp.etl.ConfigurationService;
import gov.acwi.wqp.etl.stewards.monitoringLocation.ArsMonitoringLocation;

@Component
public class MonitoringLocationProcessor implements ItemProcessor<ArsMonitoringLocation, MonitoringLocation> {

	public static final String DEFAULT_SITE_TYPE = "Not Assigned";

	private final ConfigurationService configurationService;

	@Autowired
	public MonitoringLocationProcessor(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@Override
	public MonitoringLocation process(ArsMonitoringLocation arsStation) throws Exception {
		MonitoringLocation monitoringLocation = new MonitoringLocation();

		monitoringLocation.setDataSourceId(configurationService.getEtlDataSourceId());
		monitoringLocation.setDataSource(configurationService.getEtlDataSource());
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
		monitoringLocation.setGeom(
				MonitoringLocation.calculateGeom(
						monitoringLocation.getLatitude(),
						monitoringLocation.getLongitude(),
						MonitoringLocation.DEFAULT_SRID
						)
				);
		return monitoringLocation;
	}

	protected BigDecimal getBigDecimal(String string) {
		if (NumberUtils.isCreatable(string)) {
			return NumberUtils.createBigDecimal(string);
		} else {
			return null;
		}
	}

}
