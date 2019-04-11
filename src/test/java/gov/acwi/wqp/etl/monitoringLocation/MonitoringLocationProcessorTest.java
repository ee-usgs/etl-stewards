package gov.acwi.wqp.etl.monitoringLocation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.postgis.PGgeometry;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.stewards.monitoringLocation.ArsMonitoringLocation;
import gov.acwi.wqp.etl.stewards.monitoringLocation.ArsMonitoringLocationProcessorTest;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganizationProcessorTest;

public class MonitoringLocationProcessorTest {

	public static final Integer TEST_STATION_ID = 13;
	public static final String TEST_SITE_ID = "ARS-INSJ-INSJADE";
	public static final String TEST_SITE_TYPE = "Land";
	public static final String TEST_GOVERNMENT_UNIT_CODE = "US:18:033";
	public static final BigDecimal TEST_DECIMAL_LATITUDE = new BigDecimal(ArsMonitoringLocationProcessorTest.TEST_LATITUDE);
	public static final BigDecimal TEST_DECIMAL_LONGITUDE = new BigDecimal(ArsMonitoringLocationProcessorTest.TEST_LONGITUDE);
	public static final BigDecimal TEST_DECIMAL_DRAIN_AREA = new BigDecimal(ArsMonitoringLocationProcessorTest.TEST_DRAIN_AREA);
	public static final PGgeometry TEST_GEOM = MonitoringLocation
			.calculateGeom(new BigDecimal(ArsMonitoringLocationProcessorTest.TEST_LATITUDE),
					new BigDecimal(ArsMonitoringLocationProcessorTest.TEST_LONGITUDE),
					MonitoringLocation.DEFAULT_SRID);

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
		ArsMonitoringLocation arsMonitoringLocation = buildArsMonitoringLocation(TEST_SITE_TYPE, ArsMonitoringLocationProcessorTest.TEST_HUC_12);

		MonitoringLocationProcessor processor = new MonitoringLocationProcessor();

		MonitoringLocation monitoringLocation = processor.process(arsMonitoringLocation);
		assertEquals(Application.DATA_SOURCE_ID, monitoringLocation.getDataSourceId());
		assertEquals(Application.DATA_SOURCE, monitoringLocation.getDataSource());
		assertEquals(TEST_STATION_ID, monitoringLocation.getStationId());
		assertEquals(TEST_SITE_ID, monitoringLocation.getSiteId());
		assertEquals(ArsOrganizationProcessorTest.TEST_ORG_ID, monitoringLocation.getOrganization());
		assertEquals(TEST_SITE_TYPE, monitoringLocation.getSiteType());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_HUC_12, monitoringLocation.getHuc());
		assertEquals(TEST_GOVERNMENT_UNIT_CODE, monitoringLocation.getGovernmentalUnitCode());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_ML_NAME, monitoringLocation.getStationName());
		assertEquals(ArsOrganizationProcessorTest.TEST_ORG_NAME, monitoringLocation.getOrganizationName());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_ML_DESCRIPTION, monitoringLocation.getDescriptionText());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_ML_TYPE_NAME, monitoringLocation.getStationTypeName());
		assertEquals(TEST_DECIMAL_LATITUDE, monitoringLocation.getLatitude());
		assertEquals(TEST_DECIMAL_LONGITUDE, monitoringLocation.getLongitude());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_H_METHOD, monitoringLocation.getGeopositioningMethod());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_H_REF_SYSTEM, monitoringLocation.getHdatumIdCode());
		assertEquals(TEST_DECIMAL_DRAIN_AREA, monitoringLocation.getDrainAreaValue());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_DRAIN_AREA_UNIT, monitoringLocation.getDrainAreaUnit());
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
		assertEquals(ArsOrganizationProcessorTest.TEST_ORG_ID, monitoringLocation.getOrganization());
		assertEquals(MonitoringLocationProcessor.DEFAULT_SITE_TYPE, monitoringLocation.getSiteType());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_HUC_8, monitoringLocation.getHuc());
		assertEquals(TEST_GOVERNMENT_UNIT_CODE, monitoringLocation.getGovernmentalUnitCode());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_ML_NAME, monitoringLocation.getStationName());
		assertEquals(ArsOrganizationProcessorTest.TEST_ORG_NAME, monitoringLocation.getOrganizationName());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_ML_DESCRIPTION, monitoringLocation.getDescriptionText());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_ML_TYPE_NAME, monitoringLocation.getStationTypeName());
		assertEquals(TEST_DECIMAL_LATITUDE, monitoringLocation.getLatitude());
		assertEquals(TEST_DECIMAL_LONGITUDE, monitoringLocation.getLongitude());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_H_METHOD, monitoringLocation.getGeopositioningMethod());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_H_REF_SYSTEM, monitoringLocation.getHdatumIdCode());
		assertEquals(TEST_DECIMAL_DRAIN_AREA, monitoringLocation.getDrainAreaValue());
		assertEquals(ArsMonitoringLocationProcessorTest.TEST_DRAIN_AREA_UNIT, monitoringLocation.getDrainAreaUnit());
		assertEquals(TEST_GEOM, monitoringLocation.getGeom());
	}

	private ArsMonitoringLocation buildArsMonitoringLocation(String resolvedType, String Huc12) {
		ArsMonitoringLocation arsMonitoringLocation = new ArsMonitoringLocation();

		arsMonitoringLocation.setOrganization(ArsOrganizationProcessorTest.TEST_ORG_ID);
		arsMonitoringLocation.setOrganizationName(ArsOrganizationProcessorTest.TEST_ORG_NAME);
		arsMonitoringLocation.setStationId(TEST_STATION_ID);
		arsMonitoringLocation.setMonitoringLocationIdentifier(ArsMonitoringLocationProcessorTest.TEST_ML_ID);
		arsMonitoringLocation.setMonitoringLocationName(ArsMonitoringLocationProcessorTest.TEST_ML_NAME);
		arsMonitoringLocation.setMonitoringLocationTypeName(ArsMonitoringLocationProcessorTest.TEST_ML_TYPE_NAME);
		arsMonitoringLocation.setResolvedMonitoringLocationTypeName(resolvedType);
		arsMonitoringLocation.setMonitoringLocationDescriptionText(ArsMonitoringLocationProcessorTest.TEST_ML_DESCRIPTION);
		arsMonitoringLocation.setHucEightDigitCode(ArsMonitoringLocationProcessorTest.TEST_HUC_8);
		arsMonitoringLocation.setHucTwelveDigitCode(Huc12);
		arsMonitoringLocation.setDrainageAreaMeasureValue(ArsMonitoringLocationProcessorTest.TEST_DRAIN_AREA);
		arsMonitoringLocation.setDrainageAreaMeasureUnitCode(ArsMonitoringLocationProcessorTest.TEST_DRAIN_AREA_UNIT);
		arsMonitoringLocation.setLatitudeMeasure(ArsMonitoringLocationProcessorTest.TEST_LATITUDE);
		arsMonitoringLocation.setLongitudeMeasure(ArsMonitoringLocationProcessorTest.TEST_LONGITUDE);
		arsMonitoringLocation.setHorizontalCollectionMethodName(ArsMonitoringLocationProcessorTest.TEST_H_METHOD);
		arsMonitoringLocation.setHorizontalCoordinateReferenceSystemDatumName(ArsMonitoringLocationProcessorTest.TEST_H_REF_SYSTEM);
		arsMonitoringLocation.setCountryCode(ArsMonitoringLocationProcessorTest.TEST_COUNTRY);
		arsMonitoringLocation.setStateCode(ArsMonitoringLocationProcessorTest.TEST_STATE);
		arsMonitoringLocation.setCountyCode(ArsMonitoringLocationProcessorTest.TEST_COUNTY);

		return arsMonitoringLocation;
	}
}
