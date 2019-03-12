package gov.acwi.wqp.etl.activity.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.extract.domain.ArsResult;

public class ArsResultRowMapper implements RowMapper<ArsResult> {

	@Override
	public ArsResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsResult result = new ArsResult();
		result.setDataSourceId(rs.getInt("data_source_id"));
		result.setDataSource(rs.getString("data_source"));
		result.setStationId(rs.getInt("station_id"));
		result.setSiteId(rs.getString("site_id"));
		result.setActivityId(rs.getInt("activity_id"));
		result.setActivityIdentifier(rs.getString("activity_identifier"));
		result.setActivityTypeCode(rs.getString("activity_type_code"));
		result.setActivityMediaName(rs.getString("activity_media_name"));
		result.setActivityStartDate(rs.getString("activity_start_date"));
		result.setActivityStartTime(rs.getString("activity_start_time"));
		result.setActivityStartTimeZoneCode(rs.getString("activity_start_time_zone_code"));
//		result.setMeasureValue(rs.getString("measure_value"));
//		result.setMeasureUnitCode(rs.getString("measure_unit_code"));
//		result.setActivityDepthHeightMeasure(rs.getString("activity_depth_height_measure"));
		result.setProjectIdentifier(rs.getString("project_identifier"));
		result.setMonitoringLocationIdentifier(rs.getString("monitoring_location_identifier"));
//		result.setActivityCommentText(rs.getString("activity_comment_text"));
		result.setSampleCollectionMethodIdentifier(rs.getString("sample_collection_method_identifier"));
		result.setSampleCollectionMethodIdentifierContext(rs.getString("sample_collection_method_identifier_context"));
		result.setSampleCollectionMethodName(rs.getString("sample_collection_method_name"));
		result.setSampleCollectionMethodDescriptionText(rs.getString("sample_collection_method_description_text"));
		result.setSampleCollectionEquipmentName(rs.getString("sample_collection_equipment_name"));
		result.setSampleCollectionEquipmentCommentText(rs.getString("sample_collection_equipment_comment_text"));
//		result.setResultDetectionConditionText(rs.getString("result_detection_condition_text"));
//		result.setCharacteristicName(rs.getString("characteristic_name"));
//		result.setResultSampleFractionText(rs.getString("result_sample_fraction_text"));
//		result.setResultMeasureValue(rs.getString("result_measure_value"));
//		result.setResultMeasureUnitCode(rs.getString("result_measure_unit_code"));
//		result.setResultStatusIdentifier(rs.getString("result_status_identifier"));
//		result.setResultValueTypeName(rs.getString("result_value_type_name"));
//		result.setDataQualityPrecisionValue(rs.getString("data_quality_precision_value"));
//		result.setResultCommentText(rs.getString("result_comment_text"));
//		result.setResultAnalyticalMethodIdentifier(rs.getString("result_analytical_method_identifier"));
//		result.setResultAnalyticalMethodIdentifierContext(rs.getString("result_analytical_method_identifier_context"));
//		result.setResultAnalyticalMethodName(rs.getString("result_analytical_method_name"));
//		result.setResultAnalyticalMethodDescriptionText(rs.getString("result_analytical_method_description_text"));
//		result.setDetectionQuantitationLimitTypeName(rs.getString("detection_quantitation_limit_type_name"));
//		result.setDetectionQuantitationLimitMeasureValue(rs.getString("detection_quantitation_limit_measure_value"));
//		result.setDetectionQuantitationLimitMeasureUnitCode(rs.getString("detection_quantitation_limit_measure_unit_code"));
		return result;
	}

}
