package gov.acwi.wqp.etl.extract.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArsResultActivityRowMapper implements RowMapper<ArsResult> {

	public static final String SITE_ID_COLUMN_NAME = "site_id";
	public static final String STATION_NAME_COLUMN_NAME = "station_name";
	public static final String SITE_TYPE_COLUMN_NAME = "site_type";
	public static final String HUC_COLUMN_NAME = "huc";
	public static final String GOVERNMENTAL_UNIT_CODE_COLUMN_NAME = "governmental_unit_code";
	public static final String GEOM_COLUMN_NAME = "geom";
	public static final String ACTIVITY_ID_COLUMN_NAME = "activity_id";
	public static final String ACTIVITY_IDENTIFIER_COLUMN_NAME = "activity_identifier";
	public static final String ACTIVITY_TYPE_CODE_COLUMN_NAME = "activity_type_code";
	public static final String ACTIVITY_MEDIA_NAME_COLUMN_NAME = "activity_media_name";
	public static final String ACTIVITY_START_DATE_COLUMN_NAME = "activity_start_date";
	public static final String ACTIVITY_START_TIME_COLUMN_NAME = "activity_start_time";
	public static final String ACTIVITY_START_TIME_ZONE_CODE_COLUMN_NAME = "activity_start_time_zone_code";
	public static final String SAMPLE_COLLECTION_METHOD_IDENTIFIER_COLUMN_NAME = "sample_collection_method_identifier";
	public static final String SAMPLE_COLLECTION_METHOD_IDENTIFIER_CONTEXT_COLUMN_NAME = "sample_collection_method_identifier_context";
	public static final String SAMPLE_COLLECTION_METHOD_NAME_COLUMN_NAME = "sample_collection_method_name";
	public static final String SAMPLE_COLLECTION_EQUIPMENT_NAME_COLUMN_NAME = "sample_collection_equipment_name";

	@Override
	public ArsResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsResult result = new ArsResult();
		result.setOrganization(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_COLUMN_NAME));
		result.setOrganizationName(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_NAME_COLUMN_NAME));
		result.setProjectIdentifier(rs.getString(ArsOrganizationRowMapper.PROJECT_IDENTIFIER_COLUMN_NAME));
		result.setProjectName(rs.getString(ArsOrganizationRowMapper.PROJECT_NAME_COLUMN_NAME));
		result.setStationId(rs.getInt(ArsStationRowMapper.STATION_ID_COLUMN_NAME));
		result.setSiteId(rs.getString(SITE_ID_COLUMN_NAME));
		result.setMonitoringLocationName(rs.getString(STATION_NAME_COLUMN_NAME));
		result.setResolvedMonitoringLocationTypeName(rs.getString(SITE_TYPE_COLUMN_NAME));
		result.setHucTwelveDigitCode(rs.getString(HUC_COLUMN_NAME));
		result.setGovernmentalUnitCode(rs.getString(GOVERNMENTAL_UNIT_CODE_COLUMN_NAME));
		result.setGeom(rs.getString(GEOM_COLUMN_NAME));
		result.setActivityId(rs.getInt(ACTIVITY_ID_COLUMN_NAME));
		result.setActivityIdentifier(rs.getString(ACTIVITY_IDENTIFIER_COLUMN_NAME));
		result.setActivityTypeCode(rs.getString(ACTIVITY_TYPE_CODE_COLUMN_NAME));
		result.setActivityMediaName(rs.getString(ACTIVITY_MEDIA_NAME_COLUMN_NAME));
		result.setActivityStartDate(rs.getString(ACTIVITY_START_DATE_COLUMN_NAME));
		result.setActivityStartTime(rs.getString(ACTIVITY_START_TIME_COLUMN_NAME));
		result.setActivityStartTimeZoneCode(rs.getString(ACTIVITY_START_TIME_ZONE_CODE_COLUMN_NAME));
		result.setSampleCollectionMethodIdentifier(rs.getString(SAMPLE_COLLECTION_METHOD_IDENTIFIER_COLUMN_NAME));
		result.setSampleCollectionMethodIdentifierContext(rs.getString(SAMPLE_COLLECTION_METHOD_IDENTIFIER_CONTEXT_COLUMN_NAME));
		result.setSampleCollectionMethodName(rs.getString(SAMPLE_COLLECTION_METHOD_NAME_COLUMN_NAME));
		result.setSampleCollectionEquipmentName(rs.getString(SAMPLE_COLLECTION_EQUIPMENT_NAME_COLUMN_NAME));
		return result;
	}

}
