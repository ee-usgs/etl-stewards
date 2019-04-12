package gov.acwi.wqp.etl.stewards.monitoringLocation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.acwi.wqp.etl.stewards.organization.ArsOrganizationRowMapper;

public class ArsMonitoringLocationRowMapper implements RowMapper<ArsMonitoringLocation> {

	public static final String STATION_ID = "station_id";
	public static final String MONITORING_LOCATION_IDENTIFIER = "monitoring_location_identifier";
	public static final String MONITORING_LOCATION_NAME = "monitoring_location_name";
	public static final String MONITORING_LOCATION_TYPE_NAME = "monitoring_location_type_name";
	public static final String RESOLVED_MONITORING_LOCATION_TYPE_NAME = "resolved_monitoring_location_type_name";
	public static final String MONITORING_LOCATION_DESCRIPTION_TEXT = "monitoring_location_description_text";
	public static final String HUC_EIGHT_DIGIT_CODE = "huc_eight_digit_code";
	public static final String HUC_TWELVE_DIGIT_CODE = "huc_twelve_digit_code";
	public static final String DRAINAGE_AREA_MEASURE_VALUE = "drainage_area_measure_value";
	public static final String DRAINAGE_AREA_MEASURE_UNIT_CODE = "drainage_area_measure_unit_code";
	public static final String LATITUDE_MEASURE = "latitude_measure";
	public static final String LONGITUDE_MEASURE = "longitude_measure";
	public static final String HORIZONTAL_COLLECTION_METHOD_NAME = "horizontal_collection_method_name";
	public static final String HORIZONTAL_COORDINATE_REFERENCE_SYSTEM_DATUM_NAME = "horizontal_coordinate_reference_system_datum_name";
	public static final String COUNTRY_CODE = "country_code";
	public static final String STATE_CODE = "state_code";
	public static final String COUNTY_CODE = "county_code";

	@Override
	public ArsMonitoringLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsMonitoringLocation station = new ArsMonitoringLocation();
		station.setStationId(rs.getInt(STATION_ID));
		station.setOrganization(rs.getString(ArsOrganizationRowMapper.ORGANIZATION));
		station.setOrganizationName(rs.getString(ArsOrganizationRowMapper.ORGANIZATION_NAME));
		station.setMonitoringLocationIdentifier(rs.getString(MONITORING_LOCATION_IDENTIFIER));
		station.setMonitoringLocationName(rs.getString(MONITORING_LOCATION_NAME));
		station.setMonitoringLocationTypeName(rs.getString(MONITORING_LOCATION_TYPE_NAME));
		station.setResolvedMonitoringLocationTypeName(rs.getString(RESOLVED_MONITORING_LOCATION_TYPE_NAME));
		station.setMonitoringLocationDescriptionText(rs.getString(MONITORING_LOCATION_DESCRIPTION_TEXT));
		station.setHucEightDigitCode(rs.getString(HUC_EIGHT_DIGIT_CODE));
		station.setHucTwelveDigitCode(rs.getString(HUC_TWELVE_DIGIT_CODE));
		station.setDrainageAreaMeasureValue(rs.getString(DRAINAGE_AREA_MEASURE_VALUE));
		station.setDrainageAreaMeasureUnitCode(rs.getString(DRAINAGE_AREA_MEASURE_UNIT_CODE));
		station.setLatitudeMeasure(rs.getString(LATITUDE_MEASURE));
		station.setLongitudeMeasure(rs.getString(LONGITUDE_MEASURE));
		station.setHorizontalCollectionMethodName(rs.getString(HORIZONTAL_COLLECTION_METHOD_NAME));
		station.setHorizontalCoordinateReferenceSystemDatumName(rs.getString(HORIZONTAL_COORDINATE_REFERENCE_SYSTEM_DATUM_NAME));
		station.setCountryCode(rs.getString(COUNTRY_CODE));
		station.setStateCode(rs.getString(STATE_CODE));
		station.setCountyCode(rs.getString(COUNTY_CODE));
		return station;
	}

}
