package gov.acwi.wqp.etl.stewards.monitoringLocation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.stewards.wqx.WqxMonitoringLocation;

public class ArsMonitoringLocationProcessor implements ItemProcessor<WqxMonitoringLocation, ArsMonitoringLocation>{

	@Override
	public ArsMonitoringLocation process(WqxMonitoringLocation wqxMonitoringLocation) throws Exception {
		ArsMonitoringLocation arsStation = new ArsMonitoringLocation();

		if (null != wqxMonitoringLocation.getWqxMonitoringLocationIdentity()) {
			arsStation.setMonitoringLocationIdentifier(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getMonitoringLocationIdentifier()));
			arsStation.setMonitoringLocationName(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getMonitoringLocationName()));
			arsStation.setMonitoringLocationTypeName(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getMonitoringLocationTypeName()));
			arsStation.setMonitoringLocationDescriptionText(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getMonitoringLocationDescriptionText()));
			arsStation.setHucEightDigitCode(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getHucEightDigitCode()));
			arsStation.setHucTwelveDigitCode(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getHucTwelveDigitCode()));
			if (null != wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getDrainageAreaMeasure()) {
				arsStation.setDrainageAreaMeasureValue(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getDrainageAreaMeasure().getMeasureValue()));
				arsStation.setDrainageAreaMeasureUnitCode(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationIdentity().getDrainageAreaMeasure().getMeasureUnitCode()));
			}
		}
		if (null != wqxMonitoringLocation.getWqxMonitoringLocationGeospatial()) {
			arsStation.setLatitudeMeasure(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationGeospatial().getLatitudeMeasure()));
			arsStation.setLongitudeMeasure(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationGeospatial().getLongitudeMeasure()));
			arsStation.setHorizontalCollectionMethodName(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationGeospatial().getHorizontalCollectionMethodName()));
			arsStation.setHorizontalCoordinateReferenceSystemDatumName(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationGeospatial().getHorizontalCoordinateReferenceSystemDatumName()));
			arsStation.setCountryCode(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationGeospatial().getCountryCode()));
			arsStation.setStateCode(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationGeospatial().getStateCode()));
			arsStation.setCountyCode(StringUtils.trimToNull(wqxMonitoringLocation.getWqxMonitoringLocationGeospatial().getCountyCode()));
		}

		return arsStation;
	}

}
