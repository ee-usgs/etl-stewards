package gov.acwi.wqp.etl.stewards;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.extract.domain.WqxActivity;

public class ArsResultProcessor implements ItemProcessor<WqxActivity, ArsResult>{

	@Override
	public ArsResult process(WqxActivity wqxActivity) throws Exception {
		ArsResult arsResult = new ArsResult();
		if (null != wqxActivity.getActivityDescription()) {
			arsResult.setActivityIdentifier(StringUtils.trimToNull(wqxActivity.getActivityDescription().getActivityIdentifier()));
			arsResult.setActivityTypeCode(StringUtils.trimToNull(wqxActivity.getActivityDescription().getActivityTypeCode()));
			arsResult.setActivityMediaName(StringUtils.trimToNull(wqxActivity.getActivityDescription().getActivityMediaName()));
			arsResult.setActivityStartDate(StringUtils.trimToNull(wqxActivity.getActivityDescription().getActivityStartDate()));
			if (null != wqxActivity.getActivityDescription().getActivityStartTime()) {
				arsResult.setActivityStartTime(StringUtils.trimToNull(wqxActivity.getActivityDescription().getActivityStartTime().getTime()));
				arsResult.setActivityStartTimeZoneCode(StringUtils.trimToNull(wqxActivity.getActivityDescription().getActivityStartTime().getTimeZoneCode()));
			}
			arsResult.setMeasureValue(StringUtils.trimToNull(wqxActivity.getActivityDescription().getMeasureValue()));
			arsResult.setMeasureUnitCode(StringUtils.trimToNull(wqxActivity.getActivityDescription().getMeasureUnitCode()));
			arsResult.setActivityDepthHeightMeasure(StringUtils.trimToNull(wqxActivity.getActivityDescription().getActivityDepthHeightMeasure()));
			arsResult.setProjectIdentifier(StringUtils.trimToNull(wqxActivity.getActivityDescription().getProjectIdentifier()));
			arsResult.setMonitoringLocationIdentifier(StringUtils.trimToNull(wqxActivity.getActivityDescription().getMonitoringLocationIdentifier()));
			arsResult.setActivityCommentText(StringUtils.trimToNull(wqxActivity.getActivityDescription().getActivityCommentText()));
		}
		if (null != wqxActivity.getSampleDescription()) {
			if (null != wqxActivity.getSampleDescription().getSampleCollectionMethod()) {
				arsResult.setSampleCollectionMethodIdentifier(StringUtils.trimToNull(wqxActivity.getSampleDescription().getSampleCollectionMethod().getMethodIdentifier()));
				arsResult.setSampleCollectionMethodIdentifierContext(StringUtils.trimToNull(wqxActivity.getSampleDescription().getSampleCollectionMethod().getMethodIdentifierContext()));
				arsResult.setSampleCollectionMethodName(StringUtils.trimToNull(wqxActivity.getSampleDescription().getSampleCollectionMethod().getMethodName()));
				arsResult.setSampleCollectionMethodDescriptionText(StringUtils.trimToNull(wqxActivity.getSampleDescription().getSampleCollectionMethod().getMethodDescriptionText()));
			}
			arsResult.setSampleCollectionEquipmentName(StringUtils.trimToNull(wqxActivity.getSampleDescription().getSampleCollectionEquipmentName()));
			arsResult.setSampleCollectionEquipmentCommentText(StringUtils.trimToNull(wqxActivity.getSampleDescription().getSampleCollectionEquipmentCommentText()));
		}
		if (null != wqxActivity.getResult()) {
			if (null != wqxActivity.getResult().getResultDescription()) {
				arsResult.setResultDetectionConditionText(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getResultDetectionConditionText()));
				arsResult.setCharacteristicName(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getCharacteristicName()));
				arsResult.setResultSampleFractionText(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getResultSampleFractionText()));
				if (null != wqxActivity.getResult().getResultDescription().getResultMeasure()) {
					arsResult.setResultMeasureValue(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getResultMeasure().getResultMeasureValue()));
					arsResult.setResultMeasureUnitCode(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getResultMeasure().getMeasureUnitCode()));
				}
				arsResult.setResultStatusIdentifier(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getResultStatusIdentifier()));
				arsResult.setResultValueTypeName(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getResultValueTypeName()));
				if (null != wqxActivity.getResult().getResultDescription().getDataQuality()) {
					arsResult.setDataQualityPrecisionValue(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getDataQuality().getPrecisionValue()));
				}
				arsResult.setResultCommentText(StringUtils.trimToNull(wqxActivity.getResult().getResultDescription().getResultCommentText()));
			}
			if (null != wqxActivity.getResult().getResultAnalyticalMethod()) {
				arsResult.setResultAnalyticalMethodIdentifier(StringUtils.trimToNull(wqxActivity.getResult().getResultAnalyticalMethod().getMethodIdentifier()));
				arsResult.setResultAnalyticalMethodIdentifierContext(StringUtils.trimToNull(wqxActivity.getResult().getResultAnalyticalMethod().getMethodIdentifierContext()));
				arsResult.setResultAnalyticalMethodName(StringUtils.trimToNull(wqxActivity.getResult().getResultAnalyticalMethod().getMethodName()));
				arsResult.setResultAnalyticalMethodDescriptionText(StringUtils.trimToNull(wqxActivity.getResult().getResultAnalyticalMethod().getMethodDescriptionText()));
			}
			if (null != wqxActivity.getResult().getResultLabInformation()
					&& null != wqxActivity.getResult().getResultLabInformation().getResultDetectionQuantitationLimit()) {
				arsResult.setDetectionQuantitationLimitTypeName(StringUtils.trimToNull(wqxActivity.getResult().getResultLabInformation().getResultDetectionQuantitationLimit().getDetectionQuantitationLimitTypeName()));
				if (null != wqxActivity.getResult().getResultLabInformation().getResultDetectionQuantitationLimit().getDetectionQuantitationLimitMeasure()) {
					arsResult.setDetectionQuantitationLimitMeasureValue(StringUtils.trimToNull(wqxActivity.getResult().getResultLabInformation().getResultDetectionQuantitationLimit().getDetectionQuantitationLimitMeasure().getMeasureValue()));
					arsResult.setDetectionQuantitationLimitMeasureUnitCode(StringUtils.trimToNull(wqxActivity.getResult().getResultLabInformation().getResultDetectionQuantitationLimit().getDetectionQuantitationLimitMeasure().getMeasureUnitCode()));
				}
			}
		}
		return arsResult;
	}
}
