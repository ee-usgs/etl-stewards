package gov.acwi.wqp.etl.extract.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgis.PGgeometry;
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
		ArsResult arsResult = new ArsResult();
		arsResult.setOrganization(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_COLUMN_NAME));
		arsResult.setOrganizationName(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_NAME_COLUMN_NAME));
		arsResult.setProjectIdentifier(rs.getString(ArsOrganizationRowMapper.PROJECT_IDENTIFIER_COLUMN_NAME));
		arsResult.setProjectName(rs.getString(ArsOrganizationRowMapper.PROJECT_NAME_COLUMN_NAME));
		arsResult.setStationId(rs.getInt(ArsStationRowMapper.STATION_ID_COLUMN_NAME));
		arsResult.setSiteId(rs.getString(SITE_ID_COLUMN_NAME));
		arsResult.setMonitoringLocationName(rs.getString(STATION_NAME_COLUMN_NAME));
		arsResult.setResolvedMonitoringLocationTypeName(rs.getString(SITE_TYPE_COLUMN_NAME));
		arsResult.setHucTwelveDigitCode(rs.getString(HUC_COLUMN_NAME));
		arsResult.setGovernmentalUnitCode(rs.getString(GOVERNMENTAL_UNIT_CODE_COLUMN_NAME));
		arsResult.setGeom((PGgeometry) rs.getObject(GEOM_COLUMN_NAME));
		arsResult.setActivityId(rs.getInt(ACTIVITY_ID_COLUMN_NAME));
		arsResult.setActivityIdentifier(rs.getString(ACTIVITY_IDENTIFIER_COLUMN_NAME));
		arsResult.setActivityTypeCode(rs.getString(ACTIVITY_TYPE_CODE_COLUMN_NAME));
		arsResult.setActivityMediaName(rs.getString(ACTIVITY_MEDIA_NAME_COLUMN_NAME));
		arsResult.setActivityStartDate(rs.getString(ACTIVITY_START_DATE_COLUMN_NAME));
		arsResult.setActivityStartTime(rs.getString(ACTIVITY_START_TIME_COLUMN_NAME));
		arsResult.setActivityStartTimeZoneCode(rs.getString(ACTIVITY_START_TIME_ZONE_CODE_COLUMN_NAME));
		arsResult.setSampleCollectionMethodIdentifier(rs.getString(SAMPLE_COLLECTION_METHOD_IDENTIFIER_COLUMN_NAME));
		arsResult.setSampleCollectionMethodIdentifierContext(rs.getString(SAMPLE_COLLECTION_METHOD_IDENTIFIER_CONTEXT_COLUMN_NAME));
		arsResult.setSampleCollectionMethodName(rs.getString(SAMPLE_COLLECTION_METHOD_NAME_COLUMN_NAME));
		arsResult.setSampleCollectionEquipmentName(rs.getString(SAMPLE_COLLECTION_EQUIPMENT_NAME_COLUMN_NAME));
		return arsResult;
	}

}
