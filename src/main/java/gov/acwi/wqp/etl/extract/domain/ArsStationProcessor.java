package gov.acwi.wqp.etl.extract.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

public class ArsStationProcessor implements ItemProcessor<WqxMonitoringLocation, ArsStation>{

	@Override
	public ArsStation process(WqxMonitoringLocation item) throws Exception {
		ArsStation arsStation = new ArsStation();

		if (null != item.getWqxMonitoringLocationIdentity()) {
			arsStation.setMonitoringLocationIdentifier(StringUtils.trimToNull(item.getWqxMonitoringLocationIdentity().getMonitoringLocationIdentifier()));
			arsStation.setMonitoringLocationName(StringUtils.trimToNull(item.getWqxMonitoringLocationIdentity().getMonitoringLocationName()));
			arsStation.setMonitoringLocationTypeName(StringUtils.trimToNull(item.getWqxMonitoringLocationIdentity().getMonitoringLocationTypeName()));
			arsStation.setMonitoringLocationDescriptionText(StringUtils.trimToNull(item.getWqxMonitoringLocationIdentity().getMonitoringLocationDescriptionText()));
			arsStation.setHucEightDigitCode(StringUtils.trimToNull(item.getWqxMonitoringLocationIdentity().getHucEightDigitCode()));
			arsStation.setHucTwelveDigitCode(StringUtils.trimToNull(item.getWqxMonitoringLocationIdentity().getHucTwelveDigitCode()));
			if (null != item.getWqxMonitoringLocationIdentity().getDrainageAreaMeasure()) {
				arsStation.setDrainageAreaMeasureValue(StringUtils.trimToNull(item.getWqxMonitoringLocationIdentity().getDrainageAreaMeasure().getMeasureValue()));
				arsStation.setDrainageAreaMeasureUnitCode(StringUtils.trimToNull(item.getWqxMonitoringLocationIdentity().getDrainageAreaMeasure().getMeasureUnitCode()));
			}
		}
		if (null != item.getWqxMonitoringLocationGeospatial()) {
			arsStation.setLatitudeMeasure(StringUtils.trimToNull(item.getWqxMonitoringLocationGeospatial().getLatitudeMeasure()));
			arsStation.setLongitudeMeasure(StringUtils.trimToNull(item.getWqxMonitoringLocationGeospatial().getLongitudeMeasure()));
			arsStation.setHorizontalCollectionMethodName(StringUtils.trimToNull(item.getWqxMonitoringLocationGeospatial().getHorizontalCollectionMethodName()));
			arsStation.setHorizontalCoordinateReferenceSystemDatumName(StringUtils.trimToNull(item.getWqxMonitoringLocationGeospatial().getHorizontalCoordinateReferenceSystemDatumName()));
			arsStation.setCountryCode(StringUtils.trimToNull(item.getWqxMonitoringLocationGeospatial().getCountryCode()));
			arsStation.setStateCode(StringUtils.trimToNull(item.getWqxMonitoringLocationGeospatial().getStateCode()));
			arsStation.setCountyCode(StringUtils.trimToNull(item.getWqxMonitoringLocationGeospatial().getCountyCode()));
		}

		return arsStation;
	}

}
