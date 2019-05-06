package gov.acwi.wqp.etl.stewards.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import gov.acwi.wqp.etl.BaseProcessorTest;
import gov.acwi.wqp.etl.stewards.wqx.WqxActivity;
import gov.acwi.wqp.etl.stewards.wqx.WqxActivityDescription;
import gov.acwi.wqp.etl.stewards.wqx.WqxCollectionMethod;
import gov.acwi.wqp.etl.stewards.wqx.WqxDataQuality;
import gov.acwi.wqp.etl.stewards.wqx.WqxDetectionQuantitationLimitMeasure;
import gov.acwi.wqp.etl.stewards.wqx.WqxResult;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultAnalyticalMethod;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultDescription;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultDetectionQuantitationLimit;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultLabInformation;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultMeasure;
import gov.acwi.wqp.etl.stewards.wqx.WqxSampleDescription;
import gov.acwi.wqp.etl.stewards.wqx.WqxTime;

public class ArsResultProcessorTest extends BaseProcessorTest {

	@Test
	public void happyPathTest() throws Exception {
		WqxActivity wqxActivity = new WqxActivity();
		wqxActivity.setActivityDescription(buildWqxActivityDescription(buildActivityStartTime()));
		wqxActivity.setSampleDescription(buildWqxSampleDescription(buildSampleCollectionMethod()));
		wqxActivity.setResult(
				buildWqxResult(
						buildResultDescription(buildResultMeasure(),buildDataQuality()),
						buildResultAnalyticalMethod(),
						buildResultLabInformation(
								buildResultDetectionQuantitationLimit(buildDetectionQuantitationLimitMeasure())
								)
						)
				);

		ArsResultProcessor processor = new ArsResultProcessor();

		ArsResult arsResult = processor.process(wqxActivity);

		assertEquals(TEST_ACTIVITY_IDENTIFIER, arsResult.getActivityIdentifier());
		assertEquals(TEST_ACTIVITY_TYPE_CODE, arsResult.getActivityTypeCode());
		assertEquals(TEST_ACTIVITY_MEDIA_NAME, arsResult.getActivityMediaName());
		assertEquals(TEST_ACTIVITY_START_DATE, arsResult.getActivityStartDate());
		assertEquals(TEST_ACTIVITY_START_TIME, arsResult.getActivityStartTime());
		assertEquals(TEST_ACTIVITY_START_TIMEZONE, arsResult.getActivityStartTimeZoneCode());
		assertEquals(TEST_PROJECT_ID, arsResult.getProjectIdentifier());
		assertEquals(TEST_ML_ID, arsResult.getMonitoringLocationIdentifier());
		assertEquals(TEST_ACTIVITY_COMMENT, arsResult.getActivityCommentText());
		assertEquals(TEST_SAMPLE_COLLECTION_METHOD_ID, arsResult.getSampleCollectionMethodIdentifier());
		assertEquals(TEST_SAMPLE_COLLECTION_CONTEXT, arsResult.getSampleCollectionMethodIdentifierContext());
		assertEquals(TEST_SAMPLE_COLLECTION_NAME, arsResult.getSampleCollectionMethodName());
		assertEquals(TEST_SAMPLE_COLLECTION_DESCRIPTION, arsResult.getSampleCollectionMethodDescriptionText());
		assertEquals(TEST_SAMPLE_COLLECTION_EQUIPMENT, arsResult.getSampleCollectionEquipmentName());
		assertEquals(TEST_SAMPLE_COLLECTION_EQUIPMENT_COMMENT, arsResult.getSampleCollectionEquipmentCommentText());
		assertEquals(TEST_RESULT_DETECTION_CONDITION, arsResult.getResultDetectionConditionText());
		assertEquals(TEST_CHARACTERISTIC_NAME, arsResult.getCharacteristicName());
		assertEquals(TEST_SAMPLE_FRACTION_TEXT, arsResult.getResultSampleFractionText());
		assertEquals(TEST_RESULT_MEASURE_VALUE, arsResult.getResultMeasureValue());
		assertEquals(TEST_RESULT_MEASURE_UNIT, arsResult.getResultMeasureUnitCode());
		assertEquals(TEST_RESULT_STATUS_ID, arsResult.getResultStatusIdentifier());
		assertEquals(TEST_RESULT_VALUE_TYPE_NAME, arsResult.getResultValueTypeName());
		assertEquals(TEST_DATA_QUALITY_PRECISION, arsResult.getDataQualityPrecisionValue());
		assertEquals(TEST_RESULT_COMMENT, arsResult.getResultCommentText());
		assertEquals(TEST_ANALYTICAL_METHOD_ID, arsResult.getResultAnalyticalMethodIdentifier());
		assertEquals(TEST_ANALYTICAL_METHOD_CONTEXT, arsResult.getResultAnalyticalMethodIdentifierContext());
		assertEquals(TEST_ANALYTICAL_METHOD_NAME, arsResult.getResultAnalyticalMethodName());
		assertEquals(TEST_ANALYTICAL_METHOD_DESCRIPTION, arsResult.getResultAnalyticalMethodDescriptionText());
		assertEquals(TEST_DETECTION_QUANTITATION_LIMIT_NAME, arsResult.getDetectionQuantitationLimitTypeName());
		assertEquals(TEST_DETECTION_QUANTITATION_LIMIT_VALUE, arsResult.getDetectionQuantitationLimitMeasureValue());
		assertEquals(TEST_DETECTION_QUANTITATION_LIMIT_UNIT, arsResult.getDetectionQuantitationLimitMeasureUnitCode());
	}

	@Test
	public void nullStartTimeCollectionMethodAndDQMTest() throws Exception {
		WqxActivity wqxActivity = new WqxActivity();
		wqxActivity.setActivityDescription(buildWqxActivityDescription(null));
		wqxActivity.setSampleDescription(buildWqxSampleDescription(null));
		wqxActivity.setResult(
				buildWqxResult(
						buildResultDescription(buildResultMeasure(),buildDataQuality()),
						buildResultAnalyticalMethod(),
						buildResultLabInformation(
								buildResultDetectionQuantitationLimit(null)
								)
						)
				);

		ArsResultProcessor processor = new ArsResultProcessor();

		ArsResult arsResult = processor.process(wqxActivity);

		assertEquals(TEST_ACTIVITY_IDENTIFIER, arsResult.getActivityIdentifier());
		assertEquals(TEST_ACTIVITY_TYPE_CODE, arsResult.getActivityTypeCode());
		assertEquals(TEST_ACTIVITY_MEDIA_NAME, arsResult.getActivityMediaName());
		assertEquals(TEST_ACTIVITY_START_DATE, arsResult.getActivityStartDate());
		assertNull(arsResult.getActivityStartTime());
		assertNull(arsResult.getActivityStartTimeZoneCode());
		assertEquals(TEST_PROJECT_ID, arsResult.getProjectIdentifier());
		assertEquals(TEST_ML_ID, arsResult.getMonitoringLocationIdentifier());
		assertEquals(TEST_ACTIVITY_COMMENT, arsResult.getActivityCommentText());
		assertNull(arsResult.getSampleCollectionMethodIdentifier());
		assertNull(arsResult.getSampleCollectionMethodIdentifierContext());
		assertNull(arsResult.getSampleCollectionMethodName());
		assertNull(arsResult.getSampleCollectionMethodDescriptionText());
		assertEquals(TEST_SAMPLE_COLLECTION_EQUIPMENT, arsResult.getSampleCollectionEquipmentName());
		assertEquals(TEST_SAMPLE_COLLECTION_EQUIPMENT_COMMENT, arsResult.getSampleCollectionEquipmentCommentText());
		assertEquals(TEST_RESULT_DETECTION_CONDITION, arsResult.getResultDetectionConditionText());
		assertEquals(TEST_CHARACTERISTIC_NAME, arsResult.getCharacteristicName());
		assertEquals(TEST_SAMPLE_FRACTION_TEXT, arsResult.getResultSampleFractionText());
		assertEquals(TEST_RESULT_MEASURE_VALUE, arsResult.getResultMeasureValue());
		assertEquals(TEST_RESULT_MEASURE_UNIT, arsResult.getResultMeasureUnitCode());
		assertEquals(TEST_RESULT_STATUS_ID, arsResult.getResultStatusIdentifier());
		assertEquals(TEST_RESULT_VALUE_TYPE_NAME, arsResult.getResultValueTypeName());
		assertEquals(TEST_DATA_QUALITY_PRECISION, arsResult.getDataQualityPrecisionValue());
		assertEquals(TEST_RESULT_COMMENT, arsResult.getResultCommentText());
		assertEquals(TEST_ANALYTICAL_METHOD_ID, arsResult.getResultAnalyticalMethodIdentifier());
		assertEquals(TEST_ANALYTICAL_METHOD_CONTEXT, arsResult.getResultAnalyticalMethodIdentifierContext());
		assertEquals(TEST_ANALYTICAL_METHOD_NAME, arsResult.getResultAnalyticalMethodName());
		assertEquals(TEST_ANALYTICAL_METHOD_DESCRIPTION, arsResult.getResultAnalyticalMethodDescriptionText());
		assertEquals(TEST_DETECTION_QUANTITATION_LIMIT_NAME, arsResult.getDetectionQuantitationLimitTypeName());
		assertNull(arsResult.getDetectionQuantitationLimitMeasureValue());
		assertNull(arsResult.getDetectionQuantitationLimitMeasureUnitCode());
	}

	@Test
	public void nullActivityDescSampleDescResultMeasDataQualityAndDetectTest() throws Exception {
		WqxActivity wqxActivity = new WqxActivity();
		wqxActivity.setResult(
				buildWqxResult(
						buildResultDescription(null, null),
						buildResultAnalyticalMethod(),
						buildResultLabInformation(null)
						)
				);

		ArsResultProcessor processor = new ArsResultProcessor();

		ArsResult arsResult = processor.process(wqxActivity);

		assertNull(arsResult.getActivityIdentifier());
		assertNull(arsResult.getActivityTypeCode());
		assertNull(arsResult.getActivityMediaName());
		assertNull(arsResult.getActivityStartDate());
		assertNull(arsResult.getActivityStartTime());
		assertNull(arsResult.getActivityStartTimeZoneCode());
		assertNull(arsResult.getProjectIdentifier());
		assertNull(arsResult.getMonitoringLocationIdentifier());
		assertNull(arsResult.getActivityCommentText());
		assertNull(arsResult.getSampleCollectionMethodIdentifier());
		assertNull(arsResult.getSampleCollectionMethodIdentifierContext());
		assertNull(arsResult.getSampleCollectionMethodName());
		assertNull(arsResult.getSampleCollectionMethodDescriptionText());
		assertNull(arsResult.getSampleCollectionEquipmentName());
		assertNull(arsResult.getSampleCollectionEquipmentCommentText());
		assertEquals(TEST_RESULT_DETECTION_CONDITION, arsResult.getResultDetectionConditionText());
		assertEquals(TEST_CHARACTERISTIC_NAME, arsResult.getCharacteristicName());
		assertEquals(TEST_SAMPLE_FRACTION_TEXT, arsResult.getResultSampleFractionText());
		assertNull(arsResult.getResultMeasureValue());
		assertNull(arsResult.getResultMeasureUnitCode());
		assertEquals(TEST_RESULT_STATUS_ID, arsResult.getResultStatusIdentifier());
		assertEquals(TEST_RESULT_VALUE_TYPE_NAME, arsResult.getResultValueTypeName());
		assertNull(arsResult.getDataQualityPrecisionValue());
		assertEquals(TEST_RESULT_COMMENT, arsResult.getResultCommentText());
		assertEquals(TEST_ANALYTICAL_METHOD_ID, arsResult.getResultAnalyticalMethodIdentifier());
		assertEquals(TEST_ANALYTICAL_METHOD_CONTEXT, arsResult.getResultAnalyticalMethodIdentifierContext());
		assertEquals(TEST_ANALYTICAL_METHOD_NAME, arsResult.getResultAnalyticalMethodName());
		assertEquals(TEST_ANALYTICAL_METHOD_DESCRIPTION, arsResult.getResultAnalyticalMethodDescriptionText());
		assertNull(arsResult.getDetectionQuantitationLimitTypeName());
		assertNull(arsResult.getDetectionQuantitationLimitMeasureValue());
		assertNull(arsResult.getDetectionQuantitationLimitMeasureUnitCode());
	}

	@Test
	public void nullButResultTest() throws Exception {
		WqxActivity wqxActivity = new WqxActivity();
		wqxActivity.setResult(
				buildWqxResult(null, null, null)
				);

		ArsResultProcessor processor = new ArsResultProcessor();

		ArsResult arsResult = processor.process(wqxActivity);

		assertNull(arsResult.getActivityIdentifier());
		assertNull(arsResult.getActivityTypeCode());
		assertNull(arsResult.getActivityMediaName());
		assertNull(arsResult.getActivityStartDate());
		assertNull(arsResult.getActivityStartTime());
		assertNull(arsResult.getActivityStartTimeZoneCode());
		assertNull(arsResult.getProjectIdentifier());
		assertNull(arsResult.getMonitoringLocationIdentifier());
		assertNull(arsResult.getActivityCommentText());
		assertNull(arsResult.getSampleCollectionMethodIdentifier());
		assertNull(arsResult.getSampleCollectionMethodIdentifierContext());
		assertNull(arsResult.getSampleCollectionMethodName());
		assertNull(arsResult.getSampleCollectionMethodDescriptionText());
		assertNull(arsResult.getSampleCollectionEquipmentName());
		assertNull(arsResult.getSampleCollectionEquipmentCommentText());
		assertNull(arsResult.getResultDetectionConditionText());
		assertNull(arsResult.getCharacteristicName());
		assertNull(arsResult.getResultSampleFractionText());
		assertNull(arsResult.getResultMeasureValue());
		assertNull(arsResult.getResultMeasureUnitCode());
		assertNull(arsResult.getResultStatusIdentifier());
		assertNull(arsResult.getResultValueTypeName());
		assertNull(arsResult.getDataQualityPrecisionValue());
		assertNull(arsResult.getResultCommentText());
		assertNull(arsResult.getResultAnalyticalMethodIdentifier());
		assertNull(arsResult.getResultAnalyticalMethodIdentifierContext());
		assertNull(arsResult.getResultAnalyticalMethodName());
		assertNull(arsResult.getResultAnalyticalMethodDescriptionText());
		assertNull(arsResult.getDetectionQuantitationLimitTypeName());
		assertNull(arsResult.getDetectionQuantitationLimitMeasureValue());
		assertNull(arsResult.getDetectionQuantitationLimitMeasureUnitCode());
	}

	@Test
	public void nullTest() throws Exception {
		WqxActivity wqxActivity = new WqxActivity();

		ArsResultProcessor processor = new ArsResultProcessor();

		ArsResult arsResult = processor.process(wqxActivity);

		assertNull(arsResult.getActivityIdentifier());
		assertNull(arsResult.getActivityTypeCode());
		assertNull(arsResult.getActivityMediaName());
		assertNull(arsResult.getActivityStartDate());
		assertNull(arsResult.getActivityStartTime());
		assertNull(arsResult.getActivityStartTimeZoneCode());
		assertNull(arsResult.getProjectIdentifier());
		assertNull(arsResult.getMonitoringLocationIdentifier());
		assertNull(arsResult.getActivityCommentText());
		assertNull(arsResult.getSampleCollectionMethodIdentifier());
		assertNull(arsResult.getSampleCollectionMethodIdentifierContext());
		assertNull(arsResult.getSampleCollectionMethodName());
		assertNull(arsResult.getSampleCollectionMethodDescriptionText());
		assertNull(arsResult.getSampleCollectionEquipmentName());
		assertNull(arsResult.getSampleCollectionEquipmentCommentText());
		assertNull(arsResult.getResultDetectionConditionText());
		assertNull(arsResult.getCharacteristicName());
		assertNull(arsResult.getResultSampleFractionText());
		assertNull(arsResult.getResultMeasureValue());
		assertNull(arsResult.getResultMeasureUnitCode());
		assertNull(arsResult.getResultStatusIdentifier());
		assertNull(arsResult.getResultValueTypeName());
		assertNull(arsResult.getDataQualityPrecisionValue());
		assertNull(arsResult.getResultCommentText());
		assertNull(arsResult.getResultAnalyticalMethodIdentifier());
		assertNull(arsResult.getResultAnalyticalMethodIdentifierContext());
		assertNull(arsResult.getResultAnalyticalMethodName());
		assertNull(arsResult.getResultAnalyticalMethodDescriptionText());
		assertNull(arsResult.getDetectionQuantitationLimitTypeName());
		assertNull(arsResult.getDetectionQuantitationLimitMeasureValue());
		assertNull(arsResult.getDetectionQuantitationLimitMeasureUnitCode());
	}

	private WqxActivityDescription buildWqxActivityDescription(WqxTime wqxTime) {
		WqxActivityDescription wqxActivityDescription = new WqxActivityDescription();
		wqxActivityDescription.setActivityIdentifier(SPACES.concat(TEST_ACTIVITY_IDENTIFIER).concat(SPACES));
		wqxActivityDescription.setActivityTypeCode(SPACES.concat(TEST_ACTIVITY_TYPE_CODE).concat(SPACES));
		wqxActivityDescription.setActivityMediaName(SPACES.concat(TEST_ACTIVITY_MEDIA_NAME).concat(SPACES));
		wqxActivityDescription.setActivityStartDate(SPACES.concat(TEST_ACTIVITY_START_DATE).concat(SPACES));
		wqxActivityDescription.setActivityStartTime(wqxTime);
		wqxActivityDescription.setProjectIdentifier(SPACES.concat(TEST_PROJECT_ID).concat(SPACES));
		wqxActivityDescription.setMonitoringLocationIdentifier(SPACES.concat(TEST_ML_ID).concat(SPACES));
		wqxActivityDescription.setActivityCommentText(SPACES.concat(TEST_ACTIVITY_COMMENT).concat(SPACES));
		return wqxActivityDescription;
	}

	private WqxTime buildActivityStartTime() {
		WqxTime wqxTime = new WqxTime();
		wqxTime.setTime(SPACES.concat(TEST_ACTIVITY_START_TIME).concat(SPACES));
		wqxTime.setTimeZoneCode(SPACES.concat(TEST_ACTIVITY_START_TIMEZONE).concat(SPACES));
		return wqxTime;
	}

	private WqxSampleDescription buildWqxSampleDescription(WqxCollectionMethod wqxCollectionMethod) {
		WqxSampleDescription wqxSampleDescription = new WqxSampleDescription();
		wqxSampleDescription.setSampleCollectionMethod(wqxCollectionMethod);
		wqxSampleDescription.setSampleCollectionEquipmentName(SPACES.concat(TEST_SAMPLE_COLLECTION_EQUIPMENT).concat(SPACES));
		wqxSampleDescription.setSampleCollectionEquipmentCommentText(SPACES.concat(TEST_SAMPLE_COLLECTION_EQUIPMENT_COMMENT).concat(SPACES));
		return wqxSampleDescription;
	}

	private WqxCollectionMethod buildSampleCollectionMethod() {
		WqxCollectionMethod wqxCollectionMethod = new WqxCollectionMethod();
		wqxCollectionMethod.setMethodIdentifier(SPACES.concat(TEST_SAMPLE_COLLECTION_METHOD_ID).concat(SPACES));
		wqxCollectionMethod.setMethodIdentifierContext(SPACES.concat(TEST_SAMPLE_COLLECTION_CONTEXT).concat(SPACES));
		wqxCollectionMethod.setMethodName(SPACES.concat(TEST_SAMPLE_COLLECTION_NAME).concat(SPACES));
		wqxCollectionMethod.setMethodDescriptionText(SPACES.concat(TEST_SAMPLE_COLLECTION_DESCRIPTION).concat(SPACES));
		return wqxCollectionMethod;
	}

	private WqxResult buildWqxResult(WqxResultDescription wqxResultDescription, WqxResultAnalyticalMethod wqxResultAnalyticalMethod, WqxResultLabInformation wqxResultLabInformation) {
		WqxResult wqxResult = new WqxResult();
		wqxResult.setResultDescription(wqxResultDescription);
		wqxResult.setResultAnalyticalMethod(wqxResultAnalyticalMethod);
		wqxResult.setResultLabInformation(wqxResultLabInformation);
		return wqxResult;
	}

	private WqxResultDescription buildResultDescription(WqxResultMeasure wqxResultMeasure, WqxDataQuality wqxDataQuality) {
		WqxResultDescription wqxResultDescription = new WqxResultDescription();
		wqxResultDescription.setResultDetectionConditionText(SPACES.concat(TEST_RESULT_DETECTION_CONDITION).concat(SPACES));
		wqxResultDescription.setCharacteristicName(SPACES.concat(TEST_CHARACTERISTIC_NAME).concat(SPACES));
		wqxResultDescription.setResultSampleFractionText(SPACES.concat(TEST_SAMPLE_FRACTION_TEXT).concat(SPACES));
		wqxResultDescription.setResultMeasure(wqxResultMeasure);
		wqxResultDescription.setResultStatusIdentifier(SPACES.concat(TEST_RESULT_STATUS_ID).concat(SPACES));
		wqxResultDescription.setResultValueTypeName(SPACES.concat(TEST_RESULT_VALUE_TYPE_NAME).concat(SPACES));
		wqxResultDescription.setDataQuality(wqxDataQuality);
		wqxResultDescription.setResultCommentText(SPACES.concat(TEST_RESULT_COMMENT).concat(SPACES));
		return wqxResultDescription;
	}

	private WqxResultMeasure buildResultMeasure() {
		WqxResultMeasure wqxResultMeasure = new WqxResultMeasure();
		wqxResultMeasure.setResultMeasureValue(SPACES.concat(TEST_RESULT_MEASURE_VALUE).concat(SPACES));
		wqxResultMeasure.setMeasureUnitCode(SPACES.concat(TEST_RESULT_MEASURE_UNIT).concat(SPACES));
		return wqxResultMeasure;
	}

	private WqxDataQuality buildDataQuality() {
		WqxDataQuality wqxDataQuality = new WqxDataQuality();
		wqxDataQuality.setPrecisionValue(SPACES.concat(TEST_DATA_QUALITY_PRECISION).concat(SPACES));
		return wqxDataQuality;
	}

	private WqxResultAnalyticalMethod buildResultAnalyticalMethod() {
		WqxResultAnalyticalMethod wqxResultAnalyticalMethod = new WqxResultAnalyticalMethod();
		wqxResultAnalyticalMethod.setMethodIdentifier(SPACES.concat(TEST_ANALYTICAL_METHOD_ID).concat(SPACES));
		wqxResultAnalyticalMethod.setMethodIdentifierContext(SPACES.concat(TEST_ANALYTICAL_METHOD_CONTEXT).concat(SPACES));
		wqxResultAnalyticalMethod.setMethodName(SPACES.concat(TEST_ANALYTICAL_METHOD_NAME).concat(SPACES));
		wqxResultAnalyticalMethod.setMethodDescriptionText(SPACES.concat(TEST_ANALYTICAL_METHOD_DESCRIPTION).concat(SPACES));
		return wqxResultAnalyticalMethod;
	}

	private WqxResultLabInformation buildResultLabInformation(WqxResultDetectionQuantitationLimit wqxResultDetectionQuantitationLimit) {
		WqxResultLabInformation wqxResultLabInformation = new WqxResultLabInformation();
		wqxResultLabInformation.setResultDetectionQuantitationLimit(wqxResultDetectionQuantitationLimit);
		return wqxResultLabInformation;
	}

	private WqxResultDetectionQuantitationLimit buildResultDetectionQuantitationLimit(WqxDetectionQuantitationLimitMeasure wqxDetectionQuantitationLimitMeasure) {
		WqxResultDetectionQuantitationLimit wqxResultDetectionQuantitationLimit = new WqxResultDetectionQuantitationLimit();
		wqxResultDetectionQuantitationLimit.setDetectionQuantitationLimitTypeName(SPACES.concat(TEST_DETECTION_QUANTITATION_LIMIT_NAME).concat(SPACES));
		wqxResultDetectionQuantitationLimit.setDetectionQuantitationLimitMeasure(wqxDetectionQuantitationLimitMeasure);
		return wqxResultDetectionQuantitationLimit;
	}

	private WqxDetectionQuantitationLimitMeasure buildDetectionQuantitationLimitMeasure() {
		WqxDetectionQuantitationLimitMeasure wqxDetectionQuantitationLimitMeasure = new WqxDetectionQuantitationLimitMeasure();
		wqxDetectionQuantitationLimitMeasure.setMeasureValue(SPACES.concat(TEST_DETECTION_QUANTITATION_LIMIT_VALUE).concat(SPACES));
		wqxDetectionQuantitationLimitMeasure.setMeasureUnitCode(SPACES.concat(TEST_DETECTION_QUANTITATION_LIMIT_UNIT).concat(SPACES));
		return wqxDetectionQuantitationLimitMeasure;
	}
}
