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

	public static final int DEFAULT_SRID = 4269;

	@Override
	public Result process(ArsResult item) throws Exception {
		Result result = new Result();
		result.setDataSourceId(Application.DATA_SOURCE_ID);
		result.setDataSource(Application.DATA_SOURCE);
		result.setStationId(item.getStationId());
		result.setSiteId(item.getSiteId());
		result.setEventDate(LocalDate.parse(item.getActivityStartDate()));
		result.setActivity(item.getActivityIdentifier());
		result.setSampleMedia(item.getActivityMediaName());
		result.setOrganization(item.getOrganization());
		result.setSiteType(item.getResolvedMonitoringLocationTypeName());
		result.setHuc(item.getHucTwelveDigitCode());
		result.setGovernmentalUnitCode(item.getGovernmentalUnitCode());
		result.setGeom(item.getGeom()); 
		result.setOrganizationName(item.getOrganizationName());
		result.setActivityId(item.getActivityId());
		result.setActivityTypeCode(item.getActivityTypeCode());
		result.setActivityStartTime(item.getActivityStartTime());
		result.setActStartTimeZone(item.getActivityStartTimeZoneCode());
		result.setProjectId(item.getProjectIdentifier());
		result.setProjectName(item.getProjectName());
		result.setMonitoringLocationName(item.getMonitoringLocationName());
		result.setSampleCollectMethodId(item.getSampleCollectionMethodIdentifier());
		result.setSampleCollectMethodCtx(item.getSampleCollectionMethodIdentifierContext());
		result.setSampleCollectMethodName(item.getSampleCollectionMethodName());
		result.setSampleCollectEquipName(item.getSampleCollectionEquipmentName());

		result.setResultId(item.getResultId());
		result.setResultDetectionConditionTx(item.getResultDetectionConditionText());
		result.setCharacteristicName(item.getCharacteristicName());
		result.setCharacteristicType(item.getCharacteristicType());
		result.setSampleFractionType(item.getResultSampleFractionText());
		result.setResultMeasureValue(item.getResultMeasureValue());
		result.setResultUnit(item.getResultMeasureUnitCode());
		result.setResultValueStatus(item.getResultStatusIdentifier());
		result.setResultValueType(item.getResultValueTypeName());
		result.setPrecision(item.getDataQualityPrecisionValue());
		result.setResultComment(item.getResultCommentText());
		result.setAnalyticalProcedureId(item.getResultAnalyticalMethodIdentifier());
		result.setAnalyticalProcedureSource(item.getResultAnalyticalMethodIdentifierContext());
		result.setAnalyticalMethodName(item.getResultAnalyticalMethodName());
		result.setAnalyticalMethodCitation(item.getResultAnalyticalMethodDescriptionText());
		result.setDetectionLimit(item.getDetectionQuantitationLimitMeasureValue());
		result.setDetectionLimitUnit(item.getDetectionQuantitationLimitMeasureUnitCode());
		result.setDetectionLimitDesc(item.getDetectionQuantitationLimitTypeName());
		LOG.info(result.toString());
		return result;
	}

}
