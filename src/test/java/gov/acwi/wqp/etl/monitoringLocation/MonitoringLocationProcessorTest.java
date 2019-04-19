package gov.acwi.wqp.etl.monitoringLocation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.BaseProcessorTest;
import gov.acwi.wqp.etl.stewards.monitoringLocation.ArsMonitoringLocation;

public class MonitoringLocationProcessorTest extends BaseProcessorTest {

	@Test
	public void getBigDecimalTest() {
		MonitoringLocationProcessor processor = new MonitoringLocationProcessor();
		assertNull(processor.getBigDecimal("abc"));
		assertNull(processor.getBigDecimal("a1c"));
		assertNull(processor.getBigDecimal(null));
		assertEquals(new BigDecimal("123.123"), processor.getBigDecimal("123.123"));
	}

	@Test
	public void happyPathTest() throws Exception {
		ArsMonitoringLocation arsMonitoringLocation = buildArsMonitoringLocation(TEST_SITE_TYPE, TEST_HUC_12);

		MonitoringLocationProcessor processor = new MonitoringLocationProcessor();

		MonitoringLocation monitoringLocation = processor.process(arsMonitoringLocation);
		assertEquals(Application.DATA_SOURCE_ID, monitoringLocation.getDataSourceId());
		assertEquals(Application.DATA_SOURCE, monitoringLocation.getDataSource());
		assertEquals(TEST_STATION_ID, monitoringLocation.getStationId());
		assertEquals(TEST_SITE_ID, monitoringLocation.getSiteId());
		assertEquals(TEST_ORG_ID, monitoringLocation.getOrganization());
		assertEquals(TEST_SITE_TYPE, monitoringLocation.getSiteType());
		assertEquals(TEST_HUC_12, monitoringLocation.getHuc());
		assertEquals(TEST_GOVERNMENT_UNIT_CODE, monitoringLocation.getGovernmentalUnitCode());
		assertEquals(TEST_ML_NAME, monitoringLocation.getStationName());
		assertEquals(TEST_ORG_NAME, monitoringLocation.getOrganizationName());
		assertEquals(TEST_ML_DESCRIPTION, monitoringLocation.getDescriptionText());
		assertEquals(TEST_ML_TYPE_NAME, monitoringLocation.getStationTypeName());
		assertEquals(TEST_DECIMAL_LATITUDE, monitoringLocation.getLatitude());
		assertEquals(TEST_DECIMAL_LONGITUDE, monitoringLocation.getLongitude());
		assertEquals(TEST_H_METHOD, monitoringLocation.getGeopositioningMethod());
		assertEquals(TEST_H_REF_SYSTEM, monitoringLocation.getHdatumIdCode());
		assertEquals(TEST_DECIMAL_DRAIN_AREA, monitoringLocation.getDrainAreaValue());
		assertEquals(TEST_DRAIN_AREA_UNIT, monitoringLocation.getDrainAreaUnit());
		assertEquals(TEST_GEOM, monitoringLocation.getGeom());
	}

	@Test
	public void defaultedTest() throws Exception {
		ArsMonitoringLocation arsMonitoringLocation = buildArsMonitoringLocation(null, null);

		MonitoringLocationProcessor processor = new MonitoringLocationProcessor();

		MonitoringLocation monitoringLocation = processor.process(arsMonitoringLocation);
		assertEquals(Application.DATA_SOURCE_ID, monitoringLocation.getDataSourceId());
		assertEquals(Application.DATA_SOURCE, monitoringLocation.getDataSource());
		assertEquals(TEST_STATION_ID, monitoringLocation.getStationId());
		assertEquals(TEST_SITE_ID, monitoringLocation.getSiteId());
		assertEquals(TEST_ORG_ID, monitoringLocation.getOrganization());
		assertEquals(MonitoringLocationProcessor.DEFAULT_SITE_TYPE, monitoringLocation.getSiteType());
		assertEquals(TEST_HUC_8, monitoringLocation.getHuc());
		assertEquals(TEST_GOVERNMENT_UNIT_CODE, monitoringLocation.getGovernmentalUnitCode());
		assertEquals(TEST_ML_NAME, monitoringLocation.getStationName());
		assertEquals(TEST_ORG_NAME, monitoringLocation.getOrganizationName());
		assertEquals(TEST_ML_DESCRIPTION, monitoringLocation.getDescriptionText());
		assertEquals(TEST_ML_TYPE_NAME, monitoringLocation.getStationTypeName());
		assertEquals(TEST_DECIMAL_LATITUDE, monitoringLocation.getLatitude());
		assertEquals(TEST_DECIMAL_LONGITUDE, monitoringLocation.getLongitude());
		assertEquals(TEST_H_METHOD, monitoringLocation.getGeopositioningMethod());
		assertEquals(TEST_H_REF_SYSTEM, monitoringLocation.getHdatumIdCode());
		assertEquals(TEST_DECIMAL_DRAIN_AREA, monitoringLocation.getDrainAreaValue());
		assertEquals(TEST_DRAIN_AREA_UNIT, monitoringLocation.getDrainAreaUnit());
		assertEquals(TEST_GEOM, monitoringLocation.getGeom());
	}

	private ArsMonitoringLocation buildArsMonitoringLocation(String resolvedType, String Huc12) {
		ArsMonitoringLocation arsMonitoringLocation = new ArsMonitoringLocation();

		arsMonitoringLocation.setOrganization(TEST_ORG_ID);
		arsMonitoringLocation.setOrganizationName(TEST_ORG_NAME);
		arsMonitoringLocation.setStationId(TEST_STATION_ID);
		arsMonitoringLocation.setMonitoringLocationIdentifier(TEST_ML_ID);
		arsMonitoringLocation.setMonitoringLocationName(TEST_ML_NAME);
		arsMonitoringLocation.setMonitoringLocationTypeName(TEST_ML_TYPE_NAME);
		arsMonitoringLocation.setResolvedMonitoringLocationTypeName(resolvedType);
		arsMonitoringLocation.setMonitoringLocationDescriptionText(TEST_ML_DESCRIPTION);
		arsMonitoringLocation.setHucEightDigitCode(TEST_HUC_8);
		arsMonitoringLocation.setHucTwelveDigitCode(Huc12);
		arsMonitoringLocation.setDrainageAreaMeasureValue(TEST_DRAIN_AREA);
		arsMonitoringLocation.setDrainageAreaMeasureUnitCode(TEST_DRAIN_AREA_UNIT);
		arsMonitoringLocation.setLatitudeMeasure(TEST_LATITUDE);
		arsMonitoringLocation.setLongitudeMeasure(TEST_LONGITUDE);
		arsMonitoringLocation.setHorizontalCollectionMethodName(TEST_H_METHOD);
		arsMonitoringLocation.setHorizontalCoordinateReferenceSystemDatumName(TEST_H_REF_SYSTEM);
		arsMonitoringLocation.setCountryCode(TEST_COUNTRY);
		arsMonitoringLocation.setStateCode(TEST_STATE);
		arsMonitoringLocation.setCountyCode(TEST_COUNTY);

		return arsMonitoringLocation;
	}
}
