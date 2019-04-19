package gov.acwi.wqp.etl.stewards.result;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgis.PGgeometry;
import org.springframework.jdbc.core.RowMapper;

import gov.acwi.wqp.etl.stewards.monitoringLocation.ArsMonitoringLocationRowMapper;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganizationRowMapper;

public class ArsResultActivityRowMapper implements RowMapper<ArsResult> {

	public static final String SITE_ID = "site_id";
	public static final String STATION_NAME = "station_name";
	public static final String SITE_TYPE = "site_type";
	public static final String HUC = "huc";
	public static final String GOVERNMENTAL_UNIT_CODE = "governmental_unit_code";
	public static final String GEOM = "geom";
	public static final String ACTIVITY_ID = "activity_id";
	public static final String ACTIVITY_IDENTIFIER = "activity_identifier";
	public static final String ACTIVITY_TYPE_CODE = "activity_type_code";
	public static final String ACTIVITY_MEDIA_NAME = "activity_media_name";
	public static final String ACTIVITY_START_DATE = "activity_start_date";
	public static final String ACTIVITY_START_TIME = "activity_start_time";
	public static final String ACTIVITY_START_TIME_ZONE_CODE = "activity_start_time_zone_code";
	public static final String ACTIVITY_COMMENT_TEXT = "activity_comment_text";
	public static final String SAMPLE_COLLECTION_METHOD_IDENTIFIER = "sample_collection_method_identifier";
	public static final String SAMPLE_COLLECTION_METHOD_IDENTIFIER_CONTEXT = "sample_collection_method_identifier_context";
	public static final String SAMPLE_COLLECTION_METHOD_NAME = "sample_collection_method_name";
	public static final String SAMPLE_COLLECTION_EQUIPMENT_NAME = "sample_collection_equipment_name";

	@Override
	public ArsResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsResult arsResult = new ArsResult();
		arsResult.setOrganization(rs.getString(ArsOrganizationRowMapper.ORGANIZATION));
		arsResult.setOrganizationName(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_NAME));
		arsResult.setProjectIdentifier(rs.getString(ArsOrganizationRowMapper.PROJECT_IDENTIFIER));
		arsResult.setProjectName(rs.getString(ArsOrganizationRowMapper.PROJECT_NAME));
		arsResult.setStationId(rs.getInt(ArsMonitoringLocationRowMapper.STATION_ID));
		arsResult.setSiteId(rs.getString(SITE_ID));
		arsResult.setMonitoringLocationName(rs.getString(STATION_NAME));
		arsResult.setResolvedMonitoringLocationTypeName(rs.getString(SITE_TYPE));
		arsResult.setHucTwelveDigitCode(rs.getString(HUC));
		arsResult.setGovernmentalUnitCode(rs.getString(GOVERNMENTAL_UNIT_CODE));
		arsResult.setGeom((PGgeometry) rs.getObject(GEOM));
		arsResult.setActivityId(rs.getInt(ACTIVITY_ID));
		arsResult.setActivityIdentifier(rs.getString(ACTIVITY_IDENTIFIER));
		arsResult.setActivityTypeCode(rs.getString(ACTIVITY_TYPE_CODE));
		arsResult.setActivityMediaName(rs.getString(ACTIVITY_MEDIA_NAME));
		arsResult.setActivityStartDate(rs.getString(ACTIVITY_START_DATE));
		arsResult.setActivityStartTime(rs.getString(ACTIVITY_START_TIME));
		arsResult.setActivityStartTimeZoneCode(rs.getString(ACTIVITY_START_TIME_ZONE_CODE));
		arsResult.setActivityCommentText(rs.getString(ACTIVITY_COMMENT_TEXT));
		arsResult.setSampleCollectionMethodIdentifier(rs.getString(SAMPLE_COLLECTION_METHOD_IDENTIFIER));
		arsResult.setSampleCollectionMethodIdentifierContext(rs.getString(SAMPLE_COLLECTION_METHOD_IDENTIFIER_CONTEXT));
		arsResult.setSampleCollectionMethodName(rs.getString(SAMPLE_COLLECTION_METHOD_NAME));
		arsResult.setSampleCollectionEquipmentName(rs.getString(SAMPLE_COLLECTION_EQUIPMENT_NAME));
		return arsResult;
	}

}
