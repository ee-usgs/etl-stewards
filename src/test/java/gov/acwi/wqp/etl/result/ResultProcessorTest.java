package gov.acwi.wqp.etl.result;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gov.acwi.wqp.etl.BaseProcessorTest;
import gov.acwi.wqp.etl.activity.ActivityProcessorTest;
import gov.acwi.wqp.etl.stewards.result.ArsResult;

public class ResultProcessorTest extends BaseProcessorTest {

	@Test
	public void processorTest() throws Exception {
		ArsResult arsResult = buildArsResult();

		ResultProcessor processor = new ResultProcessor(configurationService);

		Result result = processor.process(arsResult);

		ActivityProcessorTest.assertActivity(result);

		assertEquals(TEST_RESULT_ID, result.getResultId());
		assertEquals(TEST_RESULT_DETECTION_CONDITION, result.getResultDetectionConditionTx());
		assertEquals(TEST_CHARACTERISTIC_NAME, result.getCharacteristicName());
		assertEquals(TEST_CHARACTERISTIC_TYPE, result.getCharacteristicType());
		assertEquals(TEST_SAMPLE_FRACTION_TEXT, result.getSampleFractionType());
		assertEquals(TEST_RESULT_MEASURE_VALUE, result.getResultMeasureValue());
		assertEquals(TEST_RESULT_MEASURE_UNIT, result.getResultUnit());
		assertEquals(TEST_RESULT_STATUS_ID, result.getResultValueStatus());
		assertEquals(TEST_RESULT_VALUE_TYPE_NAME, result.getResultValueType());
		assertEquals(TEST_DATA_QUALITY_PRECISION, result.getPrecision());
		assertEquals(TEST_RESULT_COMMENT, result.getResultComment());
		assertEquals(TEST_ANALYTICAL_METHOD_ID, result.getAnalyticalProcedureId());
		assertEquals(TEST_ANALYTICAL_METHOD_CONTEXT, result.getAnalyticalProcedureSource());
		assertEquals(TEST_ANALYTICAL_METHOD_NAME, result.getAnalyticalMethodName());
		assertEquals(TEST_ANALYTICAL_METHOD_DESCRIPTION, result.getAnalyticalMethodCitation());
		assertEquals(TEST_DETECTION_QUANTITATION_LIMIT_VALUE, result.getDetectionLimit());
		assertEquals(TEST_DETECTION_QUANTITATION_LIMIT_UNIT, result.getDetectionLimitUnit());
		assertEquals(TEST_DETECTION_QUANTITATION_LIMIT_NAME, result.getDetectionLimitDesc());
	}

	public static ArsResult buildArsResult() {
		ArsResult arsResult = new ArsResult();

		arsResult.setStationId(BaseProcessorTest.TEST_STATION_ID);
		arsResult.setSiteId(BaseProcessorTest.TEST_SITE_ID);
		arsResult.setActivityStartDate(TEST_ACTIVITY_START_DATE);
		arsResult.setActivityIdentifier(TEST_ACTIVITY_IDENTIFIER);
		arsResult.setActivityMediaName(TEST_ACTIVITY_MEDIA_NAME);
		arsResult.setOrganization(TEST_ORG_ID);
		arsResult.setResolvedMonitoringLocationTypeName(BaseProcessorTest.TEST_SITE_TYPE);
		arsResult.setHucTwelveDigitCode(TEST_HUC_12);
		arsResult.setGovernmentalUnitCode(BaseProcessorTest.TEST_GOVERNMENT_UNIT_CODE);
		arsResult.setGeom(BaseProcessorTest.TEST_GEOM);
		arsResult.setOrganizationName(TEST_ORG_NAME);
		arsResult.setActivityId(BaseProcessorTest.TEST_ACTIVITY_ID);
		arsResult.setActivityTypeCode(TEST_ACTIVITY_TYPE_CODE);
		arsResult.setActivityStartTime(TEST_ACTIVITY_START_TIME);
		arsResult.setActivityStartTimeZoneCode(TEST_ACTIVITY_START_TIMEZONE);
		arsResult.setProjectIdentifier(TEST_PROJECT_ID);
		arsResult.setProjectName(TEST_PROJECT_NAME);
		arsResult.setMonitoringLocationName(TEST_ML_NAME);
		arsResult.setActivityCommentText(TEST_ACTIVITY_COMMENT);
		arsResult.setSampleCollectionMethodIdentifier(TEST_SAMPLE_COLLECTION_METHOD_ID);
		arsResult.setSampleCollectionMethodIdentifierContext(TEST_SAMPLE_COLLECTION_CONTEXT);
		arsResult.setSampleCollectionMethodName(TEST_SAMPLE_COLLECTION_NAME);
		arsResult.setSampleCollectionEquipmentName(TEST_SAMPLE_COLLECTION_EQUIPMENT);

		arsResult.setResultId(TEST_RESULT_ID);
		arsResult.setResultDetectionConditionText(TEST_RESULT_DETECTION_CONDITION);
		arsResult.setCharacteristicName(TEST_CHARACTERISTIC_NAME);
		arsResult.setCharacteristicType(TEST_CHARACTERISTIC_TYPE);
		arsResult.setResultSampleFractionText(TEST_SAMPLE_FRACTION_TEXT);
		arsResult.setResultMeasureValue(TEST_RESULT_MEASURE_VALUE);
		arsResult.setResultMeasureUnitCode(TEST_RESULT_MEASURE_UNIT);
		arsResult.setResultStatusIdentifier(TEST_RESULT_STATUS_ID);
		arsResult.setResultValueTypeName(TEST_RESULT_VALUE_TYPE_NAME);
		arsResult.setDataQualityPrecisionValue(TEST_DATA_QUALITY_PRECISION);
		arsResult.setResultCommentText(TEST_RESULT_COMMENT);
		arsResult.setResultAnalyticalMethodIdentifier(TEST_ANALYTICAL_METHOD_ID);
		arsResult.setResultAnalyticalMethodIdentifierContext(TEST_ANALYTICAL_METHOD_CONTEXT);
		arsResult.setResultAnalyticalMethodName(TEST_ANALYTICAL_METHOD_NAME);
		arsResult.setResultAnalyticalMethodDescriptionText(TEST_ANALYTICAL_METHOD_DESCRIPTION);
		arsResult.setDetectionQuantitationLimitMeasureValue(TEST_DETECTION_QUANTITATION_LIMIT_VALUE);
		arsResult.setDetectionQuantitationLimitMeasureUnitCode(TEST_DETECTION_QUANTITATION_LIMIT_UNIT);
		arsResult.setDetectionQuantitationLimitTypeName(TEST_DETECTION_QUANTITATION_LIMIT_NAME);
		return arsResult;
	}
}
