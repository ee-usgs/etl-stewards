package gov.acwi.wqp.etl.stewards.result;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgis.PGgeometry;
import org.springframework.jdbc.core.RowMapper;

import gov.acwi.wqp.etl.stewards.monitoringLocation.ArsMonitoringLocationRowMapper;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganizationRowMapper;

public class ArsResultRowMapper implements RowMapper<ArsResult> {

	public static final String RESULT_ID = "result_id";
	public static final String RESULT_DETECTION_CONDITION_TEXT = "result_detection_condition_text";
	public static final String CHARACTERISTIC_NAME = "characteristic_name";
	public static final String CHARACTERISTIC_TYPE = "characteristic_type";
	public static final String RESULT_SAMPLE_FRACTION_TEXT = "result_sample_fraction_text";
	public static final String RESULT_MEASURE = "result_measure_value";
	public static final String RESULT_MEASURE_UNIT_CODE = "result_measure_unit_code";
	public static final String RESULT_STATUS_IDENTIFIER = "result_status_identifier";
	public static final String RESULT_VALUE_TYPE_NAME = "result_value_type_name";
	public static final String DATA_QUALITY_PRECISION_VALUE = "data_quality_precision_value";
	public static final String RESULT_COMMENT_TEXT = "result_comment_text";
	public static final String RESULT_ANALYTICAL_METHOD_IDENTIFIER = "result_analytical_method_identifier";
	public static final String RESULT_ANALYTICAL_METHOD_IDENTIFIER_CONTEXT = "result_analytical_method_identifier_context";
	public static final String RESULT_ANALYTICAL_METHOD_NAME = "result_analytical_method_name";
	public static final String RESULT_ANALYTICAL_METHOD_DESCRIPTION_TEXT = "result_analytical_method_description_text";
	public static final String DETECTION_QUANTITATIVE_LIMIT_TYPE_NAME = "detection_quantitation_limit_type_name";
	public static final String DETECTION_QUANTITATIVE_LIMIT_MEASURE_VALUE = "detection_quantitation_limit_measure_value";
	public static final String DETECTION_QUANTITATIVE_LIMIT_MEASURE_UNIT_CODE = "detection_quantitation_limit_measure_unit_code";

	@Override
	public ArsResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsResult arsResult = new ArsResult();
		arsResult.setOrganization(rs.getString(ArsOrganizationRowMapper.ORGANIZATION));
		arsResult.setOrganizationName(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_NAME));
		arsResult.setProjectIdentifier(rs.getString(ArsOrganizationRowMapper.PROJECT_IDENTIFIER));
		arsResult.setProjectName(rs.getString(ArsOrganizationRowMapper.PROJECT_NAME));
		arsResult.setStationId(rs.getInt(ArsMonitoringLocationRowMapper.STATION_ID));
		arsResult.setSiteId(rs.getString(ArsResultActivityRowMapper.SITE_ID));
		arsResult.setMonitoringLocationName(rs.getString(ArsMonitoringLocationRowMapper.MONITORING_LOCATION_NAME));
		arsResult.setResolvedMonitoringLocationTypeName(rs.getString(ArsResultActivityRowMapper.SITE_TYPE));
		arsResult.setHucTwelveDigitCode(rs.getString(ArsResultActivityRowMapper.HUC));
		arsResult.setGovernmentalUnitCode(rs.getString(ArsResultActivityRowMapper.GOVERNMENTAL_UNIT_CODE));
		arsResult.setGeom((PGgeometry) rs.getObject(ArsResultActivityRowMapper.GEOM));
		arsResult.setActivityId(rs.getInt(ArsResultActivityRowMapper.ACTIVITY_ID));
		arsResult.setActivityIdentifier(rs.getString(ArsResultActivityRowMapper.ACTIVITY_IDENTIFIER));
		arsResult.setActivityTypeCode(rs.getString(ArsResultActivityRowMapper.ACTIVITY_TYPE_CODE));
		arsResult.setActivityMediaName(rs.getString(ArsResultActivityRowMapper.ACTIVITY_MEDIA_NAME));
		arsResult.setActivityStartDate(rs.getString(ArsResultActivityRowMapper.ACTIVITY_START_DATE));
		arsResult.setActivityStartTime(rs.getString(ArsResultActivityRowMapper.ACTIVITY_START_TIME));
		arsResult.setActivityStartTimeZoneCode(rs.getString(ArsResultActivityRowMapper.ACTIVITY_START_TIME_ZONE_CODE));
		arsResult.setActivityCommentText(rs.getString(ArsResultActivityRowMapper.ACTIVITY_COMMENT_TEXT));
		arsResult.setSampleCollectionMethodIdentifier(rs.getString(ArsResultActivityRowMapper.SAMPLE_COLLECTION_METHOD_IDENTIFIER));
		arsResult.setSampleCollectionMethodIdentifierContext(rs.getString(ArsResultActivityRowMapper.SAMPLE_COLLECTION_METHOD_IDENTIFIER_CONTEXT));
		arsResult.setSampleCollectionMethodName(rs.getString(ArsResultActivityRowMapper.SAMPLE_COLLECTION_METHOD_NAME));
		arsResult.setSampleCollectionEquipmentName(rs.getString(ArsResultActivityRowMapper.SAMPLE_COLLECTION_EQUIPMENT_NAME));

		arsResult.setResultId(rs.getInt(RESULT_ID));
		arsResult.setResultDetectionConditionText(rs.getString(RESULT_DETECTION_CONDITION_TEXT));
		arsResult.setCharacteristicName(rs.getString(CHARACTERISTIC_NAME));
		arsResult.setCharacteristicType(rs.getString(CHARACTERISTIC_TYPE));
		arsResult.setResultSampleFractionText(rs.getString(RESULT_SAMPLE_FRACTION_TEXT));
		arsResult.setResultMeasureValue(rs.getString(RESULT_MEASURE));
		arsResult.setResultMeasureUnitCode(rs.getString(RESULT_MEASURE_UNIT_CODE));
		arsResult.setResultStatusIdentifier(rs.getString(RESULT_STATUS_IDENTIFIER));
		arsResult.setResultValueTypeName(rs.getString(RESULT_VALUE_TYPE_NAME));
		arsResult.setDataQualityPrecisionValue(rs.getString(DATA_QUALITY_PRECISION_VALUE));
		arsResult.setResultCommentText(rs.getString(RESULT_COMMENT_TEXT));
		arsResult.setResultAnalyticalMethodIdentifier(rs.getString(RESULT_ANALYTICAL_METHOD_IDENTIFIER));
		arsResult.setResultAnalyticalMethodIdentifierContext(rs.getString(RESULT_ANALYTICAL_METHOD_IDENTIFIER_CONTEXT));
		arsResult.setResultAnalyticalMethodName(rs.getString(RESULT_ANALYTICAL_METHOD_NAME));
		arsResult.setResultAnalyticalMethodDescriptionText(rs.getString(RESULT_ANALYTICAL_METHOD_DESCRIPTION_TEXT));
		arsResult.setDetectionQuantitationLimitTypeName(rs.getString(DETECTION_QUANTITATIVE_LIMIT_TYPE_NAME));
		arsResult.setDetectionQuantitationLimitMeasureValue(rs.getString(DETECTION_QUANTITATIVE_LIMIT_MEASURE_VALUE));
		arsResult.setDetectionQuantitationLimitMeasureUnitCode(rs.getString(DETECTION_QUANTITATIVE_LIMIT_MEASURE_UNIT_CODE));
		return arsResult;
	}

}
