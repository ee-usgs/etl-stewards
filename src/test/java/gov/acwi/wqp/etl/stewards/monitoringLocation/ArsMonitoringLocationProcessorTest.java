package gov.acwi.wqp.etl.stewards.monitoringLocation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import gov.acwi.wqp.etl.BaseProcessorTest;
import gov.acwi.wqp.etl.stewards.wqx.WqxDrainageAreaMeasure;
import gov.acwi.wqp.etl.stewards.wqx.WqxMonitoringLocation;
import gov.acwi.wqp.etl.stewards.wqx.WqxMonitoringLocationGeospatial;
import gov.acwi.wqp.etl.stewards.wqx.WqxMonitoringLocationIdentity;

public class ArsMonitoringLocationProcessorTest extends BaseProcessorTest {

	@Test
	public void happyPathTest() throws Exception {
		WqxMonitoringLocation wqxMonitoringLocation = new WqxMonitoringLocation();
		wqxMonitoringLocation.setWqxMonitoringLocationIdentity(buildWqxMonitoringLocationIdentity(buildWqxDrainageAreaMeasure()));
		wqxMonitoringLocation.setWqxMonitoringLocationGeospatial(buildWqxMonitoringLocationGeospatial());

		ArsMonitoringLocationProcessor processor = new ArsMonitoringLocationProcessor();

		ArsMonitoringLocation arsMonitoringLocation = processor.process(wqxMonitoringLocation);

		assertEquals(TEST_ML_ID, arsMonitoringLocation.getMonitoringLocationIdentifier());
		assertEquals(TEST_ML_NAME, arsMonitoringLocation.getMonitoringLocationName());
		assertEquals(TEST_ML_TYPE_NAME, arsMonitoringLocation.getMonitoringLocationTypeName());
		assertEquals(TEST_ML_DESCRIPTION, arsMonitoringLocation.getMonitoringLocationDescriptionText());
		assertEquals(TEST_HUC_8, arsMonitoringLocation.getHucEightDigitCode());
		assertEquals(TEST_HUC_12, arsMonitoringLocation.getHucTwelveDigitCode());

		assertEquals(TEST_DRAIN_AREA, arsMonitoringLocation.getDrainageAreaMeasureValue());
		assertEquals(TEST_DRAIN_AREA_UNIT, arsMonitoringLocation.getDrainageAreaMeasureUnitCode());

		assertEquals(TEST_LATITUDE, arsMonitoringLocation.getLatitudeMeasure());
		assertEquals(TEST_LONGITUDE, arsMonitoringLocation.getLongitudeMeasure());
		assertEquals(TEST_H_METHOD, arsMonitoringLocation.getHorizontalCollectionMethodName());
		assertEquals(TEST_H_REF_SYSTEM, arsMonitoringLocation.getHorizontalCoordinateReferenceSystemDatumName());
		assertEquals(TEST_COUNTRY, arsMonitoringLocation.getCountryCode());
		assertEquals(TEST_STATE, arsMonitoringLocation.getStateCode());
		assertEquals(TEST_COUNTY, arsMonitoringLocation.getCountyCode());
	}

	@Test
	public void nullDrainageTest() throws Exception {
		WqxMonitoringLocation wqxMonitoringLocation = new WqxMonitoringLocation();
		wqxMonitoringLocation.setWqxMonitoringLocationIdentity(buildWqxMonitoringLocationIdentity(null));
		wqxMonitoringLocation.setWqxMonitoringLocationGeospatial(buildWqxMonitoringLocationGeospatial());

		ArsMonitoringLocationProcessor processor = new ArsMonitoringLocationProcessor();

		ArsMonitoringLocation arsMonitoringLocation = processor.process(wqxMonitoringLocation);

		assertEquals(TEST_ML_ID, arsMonitoringLocation.getMonitoringLocationIdentifier());
		assertEquals(TEST_ML_NAME, arsMonitoringLocation.getMonitoringLocationName());
		assertEquals(TEST_ML_TYPE_NAME, arsMonitoringLocation.getMonitoringLocationTypeName());
		assertEquals(TEST_ML_DESCRIPTION, arsMonitoringLocation.getMonitoringLocationDescriptionText());
		assertEquals(TEST_HUC_8, arsMonitoringLocation.getHucEightDigitCode());
		assertEquals(TEST_HUC_12, arsMonitoringLocation.getHucTwelveDigitCode());

		assertNull(arsMonitoringLocation.getDrainageAreaMeasureValue());
		assertNull(arsMonitoringLocation.getDrainageAreaMeasureUnitCode());

		assertEquals(TEST_LATITUDE, arsMonitoringLocation.getLatitudeMeasure());
		assertEquals(TEST_LONGITUDE, arsMonitoringLocation.getLongitudeMeasure());
		assertEquals(TEST_H_METHOD, arsMonitoringLocation.getHorizontalCollectionMethodName());
		assertEquals(TEST_H_REF_SYSTEM, arsMonitoringLocation.getHorizontalCoordinateReferenceSystemDatumName());
		assertEquals(TEST_COUNTRY, arsMonitoringLocation.getCountryCode());
		assertEquals(TEST_STATE, arsMonitoringLocation.getStateCode());
		assertEquals(TEST_COUNTY, arsMonitoringLocation.getCountyCode());
	}

	@Test
	public void nullIdentityTest() throws Exception {
		WqxMonitoringLocation wqxMonitoringLocation = new WqxMonitoringLocation();
		wqxMonitoringLocation.setWqxMonitoringLocationGeospatial(buildWqxMonitoringLocationGeospatial());

		ArsMonitoringLocationProcessor processor = new ArsMonitoringLocationProcessor();

		ArsMonitoringLocation arsMonitoringLocation = processor.process(wqxMonitoringLocation);

		assertNull(arsMonitoringLocation.getMonitoringLocationIdentifier());
		assertNull(arsMonitoringLocation.getMonitoringLocationName());
		assertNull(arsMonitoringLocation.getMonitoringLocationTypeName());
		assertNull(arsMonitoringLocation.getMonitoringLocationDescriptionText());
		assertNull(arsMonitoringLocation.getHucEightDigitCode());
		assertNull(arsMonitoringLocation.getHucTwelveDigitCode());

		assertNull(arsMonitoringLocation.getDrainageAreaMeasureValue());
		assertNull(arsMonitoringLocation.getDrainageAreaMeasureUnitCode());

		assertEquals(TEST_LATITUDE, arsMonitoringLocation.getLatitudeMeasure());
		assertEquals(TEST_LONGITUDE, arsMonitoringLocation.getLongitudeMeasure());
		assertEquals(TEST_H_METHOD, arsMonitoringLocation.getHorizontalCollectionMethodName());
		assertEquals(TEST_H_REF_SYSTEM, arsMonitoringLocation.getHorizontalCoordinateReferenceSystemDatumName());
		assertEquals(TEST_COUNTRY, arsMonitoringLocation.getCountryCode());
		assertEquals(TEST_STATE, arsMonitoringLocation.getStateCode());
		assertEquals(TEST_COUNTY, arsMonitoringLocation.getCountyCode());
	}

	@Test
	public void nullGeospatialTest() throws Exception {
		WqxMonitoringLocation wqxMonitoringLocation = new WqxMonitoringLocation();
		wqxMonitoringLocation.setWqxMonitoringLocationIdentity(buildWqxMonitoringLocationIdentity(buildWqxDrainageAreaMeasure()));

		ArsMonitoringLocationProcessor processor = new ArsMonitoringLocationProcessor();

		ArsMonitoringLocation arsMonitoringLocation = processor.process(wqxMonitoringLocation);

		assertEquals(TEST_ML_ID, arsMonitoringLocation.getMonitoringLocationIdentifier());
		assertEquals(TEST_ML_NAME, arsMonitoringLocation.getMonitoringLocationName());
		assertEquals(TEST_ML_TYPE_NAME, arsMonitoringLocation.getMonitoringLocationTypeName());
		assertEquals(TEST_ML_DESCRIPTION, arsMonitoringLocation.getMonitoringLocationDescriptionText());
		assertEquals(TEST_HUC_8, arsMonitoringLocation.getHucEightDigitCode());
		assertEquals(TEST_HUC_12, arsMonitoringLocation.getHucTwelveDigitCode());

		assertEquals(TEST_DRAIN_AREA, arsMonitoringLocation.getDrainageAreaMeasureValue());
		assertEquals(TEST_DRAIN_AREA_UNIT, arsMonitoringLocation.getDrainageAreaMeasureUnitCode());

		assertNull(arsMonitoringLocation.getLatitudeMeasure());
		assertNull(arsMonitoringLocation.getLongitudeMeasure());
		assertNull(arsMonitoringLocation.getHorizontalCollectionMethodName());
		assertNull(arsMonitoringLocation.getHorizontalCoordinateReferenceSystemDatumName());
		assertNull(arsMonitoringLocation.getCountryCode());
		assertNull(arsMonitoringLocation.getStateCode());
		assertNull(arsMonitoringLocation.getCountyCode());
	}

	private WqxDrainageAreaMeasure buildWqxDrainageAreaMeasure() {
		WqxDrainageAreaMeasure wqxDrainageAreaMeasure = new WqxDrainageAreaMeasure();
		wqxDrainageAreaMeasure.setMeasureValue(SPACES.concat(TEST_DRAIN_AREA).concat(SPACES));
		wqxDrainageAreaMeasure.setMeasureUnitCode(SPACES.concat(TEST_DRAIN_AREA_UNIT).concat(SPACES));
		return wqxDrainageAreaMeasure;
	}

	private WqxMonitoringLocationIdentity buildWqxMonitoringLocationIdentity(WqxDrainageAreaMeasure wqxDrainageAreaMeasure) {
		WqxMonitoringLocationIdentity wqxMonitoringLocationIdentity = new WqxMonitoringLocationIdentity();
		wqxMonitoringLocationIdentity.setMonitoringLocationIdentifier(SPACES.concat(TEST_ML_ID).concat(SPACES));
		wqxMonitoringLocationIdentity.setMonitoringLocationName(SPACES.concat(TEST_ML_NAME).concat(SPACES));
		wqxMonitoringLocationIdentity.setMonitoringLocationTypeName(SPACES.concat(TEST_ML_TYPE_NAME).concat(SPACES));
		wqxMonitoringLocationIdentity.setMonitoringLocationDescriptionText(SPACES.concat(TEST_ML_DESCRIPTION).concat(SPACES));
		wqxMonitoringLocationIdentity.setHucEightDigitCode(SPACES.concat(TEST_HUC_8).concat(SPACES));
		wqxMonitoringLocationIdentity.setHucTwelveDigitCode(SPACES.concat(TEST_HUC_12).concat(SPACES));
		wqxMonitoringLocationIdentity.setWqxDrainageAreaMeasure(wqxDrainageAreaMeasure);
		return wqxMonitoringLocationIdentity;
	}

	private WqxMonitoringLocationGeospatial buildWqxMonitoringLocationGeospatial() {
		WqxMonitoringLocationGeospatial wqxMonitoringLocationGeospatial = new WqxMonitoringLocationGeospatial();
		wqxMonitoringLocationGeospatial.setLatitudeMeasure(SPACES.concat(TEST_LATITUDE).concat(SPACES));
		wqxMonitoringLocationGeospatial.setLongitudeMeasure(SPACES.concat(TEST_LONGITUDE).concat(SPACES));
		wqxMonitoringLocationGeospatial.setHorizontalCollectionMethodName(SPACES.concat(TEST_H_METHOD).concat(SPACES));
		wqxMonitoringLocationGeospatial.setHorizontalCoordinateReferenceSystemDatumName(SPACES.concat(TEST_H_REF_SYSTEM).concat(SPACES));
		wqxMonitoringLocationGeospatial.setCountryCode(SPACES.concat(TEST_COUNTRY).concat(SPACES));
		wqxMonitoringLocationGeospatial.setStateCode(SPACES.concat(TEST_STATE).concat(SPACES));
		wqxMonitoringLocationGeospatial.setCountyCode(SPACES.concat(TEST_COUNTY).concat(SPACES));
		return wqxMonitoringLocationGeospatial;
	}

}
