package gov.acwi.wqp.etl.result;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.extract.domain.ArsResult;
import gov.acwi.wqp.etl.result.Result;

public class ResultProcessor implements ItemProcessor<ArsResult, Result>{
	private static final Logger LOG = LoggerFactory.getLogger(ResultProcessor.class);

	@Override
	public Result process(ArsResult arsResult) throws Exception {
		Result result = new Result();
		result.setDataSourceId(Application.DATA_SOURCE_ID);
		result.setDataSource(Application.DATA_SOURCE);
		result.setStationId(arsResult.getStationId());
		result.setSiteId(arsResult.getSiteId());
		result.setEventDate(LocalDate.parse(arsResult.getActivityStartDate()));
		result.setActivity(arsResult.getActivityIdentifier());
		result.setSampleMedia(arsResult.getActivityMediaName());
		result.setOrganization(arsResult.getOrganization());
		result.setSiteType(arsResult.getResolvedMonitoringLocationTypeName());
		result.setHuc(arsResult.getHucTwelveDigitCode());
		result.setGovernmentalUnitCode(arsResult.getGovernmentalUnitCode());
		result.setGeom(arsResult.getGeom()); 
		result.setOrganizationName(arsResult.getOrganizationName());
		result.setActivityId(arsResult.getActivityId());
		result.setActivityTypeCode(arsResult.getActivityTypeCode());
		result.setActivityStartTime(arsResult.getActivityStartTime());
		result.setActStartTimeZone(arsResult.getActivityStartTimeZoneCode());
		result.setProjectId(arsResult.getProjectIdentifier());
		result.setProjectName(arsResult.getProjectName());
		result.setMonitoringLocationName(arsResult.getMonitoringLocationName());
		result.setSampleCollectMethodId(arsResult.getSampleCollectionMethodIdentifier());
		result.setSampleCollectMethodCtx(arsResult.getSampleCollectionMethodIdentifierContext());
		result.setSampleCollectMethodName(arsResult.getSampleCollectionMethodName());
		result.setSampleCollectEquipName(arsResult.getSampleCollectionEquipmentName());

		result.setResultId(arsResult.getResultId());
		result.setResultDetectionConditionTx(arsResult.getResultDetectionConditionText());
		result.setCharacteristicName(arsResult.getCharacteristicName());
		result.setCharacteristicType(arsResult.getCharacteristicType());
		result.setSampleFractionType(arsResult.getResultSampleFractionText());
		result.setResultMeasureValue(arsResult.getResultMeasureValue());
		result.setResultUnit(arsResult.getResultMeasureUnitCode());
		result.setResultValueStatus(arsResult.getResultStatusIdentifier());
		result.setResultValueType(arsResult.getResultValueTypeName());
		result.setPrecision(arsResult.getDataQualityPrecisionValue());
		result.setResultComment(arsResult.getResultCommentText());
		result.setAnalyticalProcedureId(arsResult.getResultAnalyticalMethodIdentifier());
		result.setAnalyticalProcedureSource(arsResult.getResultAnalyticalMethodIdentifierContext());
		result.setAnalyticalMethodName(arsResult.getResultAnalyticalMethodName());
		result.setAnalyticalMethodCitation(arsResult.getResultAnalyticalMethodDescriptionText());
		result.setDetectionLimit(arsResult.getDetectionQuantitationLimitMeasureValue());
		result.setDetectionLimitUnit(arsResult.getDetectionQuantitationLimitMeasureUnitCode());
		result.setDetectionLimitDesc(arsResult.getDetectionQuantitationLimitTypeName());
		LOG.info(result.toString());
		return result;
	}

}
