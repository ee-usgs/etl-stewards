package gov.acwi.wqp.etl.extract.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArsStationRowMapper implements RowMapper<ArsStation> {

	public static final String STATION_ID_COLUMN_NAME = "station_id";
	public static final String MONITORING_LOCATION_IDENTIFIER_COLUMN_NAME = "monitoring_location_identifier";
	public static final String MONITORING_LOCATION_NAME_COLUMN_NAME = "monitoring_location_name";
	public static final String MONITORING_LOCATION_TYPE_NAME_COLUMN_NAME = "monitoring_location_type_name";
	public static final String RESOLVED_MONITORING_LOCATION_TYPE_NAME_COLUMN_NAME = "resolved_monitoring_location_type_name";
	public static final String MONITORING_LOCATION_DESCRIPTION_TEXT_COLUMN_NAME = "monitoring_location_description_text";
	public static final String HUC_EIGHT_DIGIT_CODE_COLUMN_NAME = "huc_eight_digit_code";
	public static final String HUC_TWELVE_DIGIT_CODE_COLUMN_NAME = "huc_twelve_digit_code";
	public static final String DRAINAGE_AREA_MEASURE_VALUE_COLUMN_NAME = "drainage_area_measure_value";
	public static final String DRAINAGE_AREA_MEASURE_UNIT_CODE_COLUMN_NAME = "drainage_area_measure_unit_code";
	public static final String LATITUDE_MEASURE_COLUMN_NAME = "latitude_measure";
	public static final String LONGITUDE_MEASURE_COLUMN_NAME = "longitude_measure";
	public static final String HORIZONTAL_COLLECTION_METHOD_NAME_COLUMN_NAME = "horizontal_collection_method_name";
	public static final String HORIZONTAL_COORDINATE_REFERENCE_SYSTEM_DATUM_NAME_COLUMN_NAME = "horizontal_coordinate_reference_system_datum_name";
	public static final String COUNTRY_CODE_COLUMN_NAME = "country_code";
	public static final String STATE_CODE_COLUMN_NAME = "state_code";
	public static final String COUNTY_CODE_COLUMN_NAME = "county_code";

	@Override
	public ArsStation mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsStation station = new ArsStation();
		station.setStationId(rs.getInt(STATION_ID_COLUMN_NAME));
		station.setOrganization(rs.getNString(ArsOrganizationRowMapper.ORGANIZATION_COLUMN_NAME));
		station.setOrganizationName(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_NAME_COLUMN_NAME));
		station.setMonitoringLocationIdentifier(rs.getString(MONITORING_LOCATION_IDENTIFIER_COLUMN_NAME));
		station.setMonitoringLocationName(rs.getString(MONITORING_LOCATION_NAME_COLUMN_NAME));
		station.setMonitoringLocationTypeName(rs.getString(MONITORING_LOCATION_TYPE_NAME_COLUMN_NAME));
		station.setResolvedMonitoringLocationTypeName(rs.getString(RESOLVED_MONITORING_LOCATION_TYPE_NAME_COLUMN_NAME));
		station.setMonitoringLocationDescriptionText(rs.getString(MONITORING_LOCATION_DESCRIPTION_TEXT_COLUMN_NAME));
		station.setHucEightDigitCode(rs.getString(HUC_EIGHT_DIGIT_CODE_COLUMN_NAME));
		station.setHucTwelveDigitCode(rs.getString(HUC_TWELVE_DIGIT_CODE_COLUMN_NAME));
		station.setDrainageAreaMeasureValue(rs.getString(DRAINAGE_AREA_MEASURE_VALUE_COLUMN_NAME));
		station.setDrainageAreaMeasureUnitCode(rs.getString(DRAINAGE_AREA_MEASURE_UNIT_CODE_COLUMN_NAME));
		station.setLatitudeMeasure(rs.getString(LATITUDE_MEASURE_COLUMN_NAME));
		station.setLongitudeMeasure(rs.getString(LONGITUDE_MEASURE_COLUMN_NAME));
		station.setHorizontalCollectionMethodName(rs.getString(HORIZONTAL_COLLECTION_METHOD_NAME_COLUMN_NAME));
		station.setHorizontalCoordinateReferenceSystemDatumName(rs.getString(HORIZONTAL_COORDINATE_REFERENCE_SYSTEM_DATUM_NAME_COLUMN_NAME));
		station.setCountryCode(rs.getString(COUNTRY_CODE_COLUMN_NAME));
		station.setStateCode(rs.getString(STATE_CODE_COLUMN_NAME));
		station.setCountyCode(rs.getString(COUNTY_CODE_COLUMN_NAME));
		return station;
	}

}
