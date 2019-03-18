package gov.acwi.wqp.etl.extract.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.acwi.wqp.etl.Application;

public class ArsResultResultRowMapper implements RowMapper<ArsResult> {

	public static final String RESULT_ID_COLUMN_NAME = "result_id";
	public static final String RESULT_DETECTION_CONDITION_TEXT_COLUMN_NAME = "result_detection_condition_text";
	public static final String CHARACTERISTIC_NAME_COLUMN_NAME = "characteristic_name";
	public static final String CHARACTERISTIC_TYPE_COLUMN_NAME = "characteristic_type";
	public static final String RESULT_SAMPLE_FRACTION_TEXT_COLUMN_NAME = "result_sample_fraction_text";
	public static final String RESULT_MEASURE_COLUMN_NAME = "result_measure_value";
	public static final String RESULT_MEASURE_UNIT_CODE_COLUMN_NAME = "result_measure_unit_code";
	public static final String RESULT_STATUS_IDENTIFIER_COLUMN_NAME = "result_status_identifier";
	public static final String RESULT_VALUE_TYPE_NAME_COLUMN_NAME = "result_value_type_name";
	public static final String DATA_QUALITY_PRECISION_VALUE_COLUMN_NAME = "data_quality_precision_value";
	public static final String RESULT_COMMENT_TEXT_COLUMN_NAME = "result_comment_text";
	public static final String RESULT_ANALYTICAL_METHOD_IDENTIFIER_COLUMN_NAME = "result_analytical_method_identifier";
	public static final String RESULT_ANALYTICAL_METHOD_IDENTIFIER_CONTEXT_COLUMN_NAME = "result_analytical_method_identifier_context";
	public static final String RESULT_ANALYTICAL_METHOD_NAME_COLUMN_NAME = "result_analytical_method_name";
	public static final String RESULT_ANALYTICAL_METHOD_DESCRIPTION_TEXT_COLUMN_NAME = "result_analytical_method_description_text";
	public static final String DETECTION_QUANTITATIVE_LIMIT_TYPE_NAME_COLUMN_NAME = "detection_quantitation_limit_type_name";
	public static final String DETECTION_QUANTITATIVE_LIMIT_MEASURE_VALUE_COLUMN_NAME = "detection_quantitation_limit_measure_value";
	public static final String DETECTION_QUANTITATIVE_LIMIT_MEASURE_UNIT_CODE_COLUMN_NAME = "detection_quantitation_limit_measure_unit_code";

	@Override
	public ArsResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsResult result = new ArsResult();
		result.setOrganization(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_COLUMN_NAME));
		result.setOrganizationName(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_NAME_COLUMN_NAME));
		result.setProjectIdentifier(rs.getString(ArsOrganizationRowMapper.PROJECT_IDENTIFIER_COLUMN_NAME));
		result.setProjectName(rs.getString(ArsOrganizationRowMapper.PROJECT_NAME_COLUMN_NAME));
		result.setStationId(rs.getInt(ArsStationRowMapper.STATION_ID_COLUMN_NAME));
		result.setSiteId(rs.getString(ArsResultActivityRowMapper.SITE_ID_COLUMN_NAME));
		result.setMonitoringLocationName(rs.getString(ArsStationRowMapper.MONITORING_LOCATION_NAME_COLUMN_NAME));
		result.setResolvedMonitoringLocationTypeName(rs.getString(ArsResultActivityRowMapper.SITE_TYPE_COLUMN_NAME));
		result.setHucTwelveDigitCode(rs.getString(ArsResultActivityRowMapper.HUC_COLUMN_NAME));
		result.setGovernmentalUnitCode(rs.getString(ArsResultActivityRowMapper.GOVERNMENTAL_UNIT_CODE_COLUMN_NAME));
		result.setGeom(rs.getString(ArsResultActivityRowMapper.GEOM_COLUMN_NAME));
		result.setActivityId(rs.getInt(ArsResultActivityRowMapper.ACTIVITY_ID_COLUMN_NAME));
		result.setActivityIdentifier(rs.getString(ArsResultActivityRowMapper.ACTIVITY_IDENTIFIER_COLUMN_NAME));
		result.setActivityTypeCode(rs.getString(ArsResultActivityRowMapper.ACTIVITY_TYPE_CODE_COLUMN_NAME));
		result.setActivityMediaName(rs.getString(ArsResultActivityRowMapper.ACTIVITY_MEDIA_NAME_COLUMN_NAME));
		result.setActivityStartDate(rs.getString(ArsResultActivityRowMapper.ACTIVITY_START_DATE_COLUMN_NAME));
		result.setActivityStartTime(rs.getString(ArsResultActivityRowMapper.ACTIVITY_START_TIME_COLUMN_NAME));
		result.setActivityStartTimeZoneCode(rs.getString(ArsResultActivityRowMapper.ACTIVITY_START_TIME_ZONE_CODE_COLUMN_NAME));
		result.setSampleCollectionMethodIdentifier(rs.getString(ArsResultActivityRowMapper.SAMPLE_COLLECTION_METHOD_IDENTIFIER_COLUMN_NAME));
		result.setSampleCollectionMethodIdentifierContext(rs.getString(ArsResultActivityRowMapper.SAMPLE_COLLECTION_METHOD_IDENTIFIER_CONTEXT_COLUMN_NAME));
		result.setSampleCollectionMethodName(rs.getString(ArsResultActivityRowMapper.SAMPLE_COLLECTION_METHOD_NAME_COLUMN_NAME));
		result.setSampleCollectionEquipmentName(rs.getString(ArsResultActivityRowMapper.SAMPLE_COLLECTION_EQUIPMENT_NAME_COLUMN_NAME));

		result.setResultId(rs.getInt(RESULT_ID_COLUMN_NAME));
		result.setResultDetectionConditionText(rs.getString(RESULT_DETECTION_CONDITION_TEXT_COLUMN_NAME));
		result.setCharacteristicName(rs.getString(CHARACTERISTIC_NAME_COLUMN_NAME));
		result.setCharacteristicType(rs.getString(CHARACTERISTIC_TYPE_COLUMN_NAME));
		result.setResultSampleFractionText(rs.getString(RESULT_SAMPLE_FRACTION_TEXT_COLUMN_NAME));
		result.setResultMeasureValue(rs.getString(RESULT_MEASURE_COLUMN_NAME));
		result.setResultMeasureUnitCode(rs.getString(RESULT_MEASURE_UNIT_CODE_COLUMN_NAME));
		result.setResultStatusIdentifier(rs.getString(RESULT_STATUS_IDENTIFIER_COLUMN_NAME));
		result.setResultValueTypeName(rs.getString(RESULT_VALUE_TYPE_NAME_COLUMN_NAME));
		result.setDataQualityPrecisionValue(rs.getString(DATA_QUALITY_PRECISION_VALUE_COLUMN_NAME));
		result.setResultCommentText(rs.getString(RESULT_COMMENT_TEXT_COLUMN_NAME));
		result.setResultAnalyticalMethodIdentifier(rs.getString(RESULT_ANALYTICAL_METHOD_IDENTIFIER_COLUMN_NAME));
		result.setResultAnalyticalMethodIdentifierContext(rs.getString(RESULT_ANALYTICAL_METHOD_IDENTIFIER_CONTEXT_COLUMN_NAME));
		result.setResultAnalyticalMethodName(rs.getString(RESULT_ANALYTICAL_METHOD_NAME_COLUMN_NAME));
		result.setResultAnalyticalMethodDescriptionText(rs.getString(RESULT_ANALYTICAL_METHOD_DESCRIPTION_TEXT_COLUMN_NAME));
		result.setDetectionQuantitationLimitTypeName(rs.getString(DETECTION_QUANTITATIVE_LIMIT_TYPE_NAME_COLUMN_NAME));
		result.setDetectionQuantitationLimitMeasureValue(rs.getString(DETECTION_QUANTITATIVE_LIMIT_MEASURE_VALUE_COLUMN_NAME));
		result.setDetectionQuantitationLimitMeasureUnitCode(rs.getString(DETECTION_QUANTITATIVE_LIMIT_MEASURE_UNIT_CODE_COLUMN_NAME));
		return result;
	}

}
