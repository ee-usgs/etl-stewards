package gov.acwi.wqp.etl.monitoringLocation.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.acwi.wqp.etl.extract.domain.ArsStation;

public class ArsStationRowMapper implements RowMapper<ArsStation> {

	@Override
	public ArsStation mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsStation station = new ArsStation();
		station.setStationId(rs.getInt("station_id"));
		station.setOrganization(rs.getNString("organization"));
		station.setOrganizationName(rs.getString("organization_name"));
		station.setMonitoringLocationIdentifier(rs.getString("monitoring_location_identifier"));
		station.setMonitoringLocationName(rs.getString("monitoring_location_name"));
		station.setMonitoringLocationTypeName(rs.getString("monitoring_location_type_name"));
		station.setResolvedMonitoringLocationTypeName(rs.getString("primary_site_type"));
		station.setMonitoringLocationDescriptionText(rs.getString("monitoring_location_description_text"));
		station.setHucEightDigitCode(rs.getString("huc_eight_digit_code"));
		station.setHucTwelveDigitCode(rs.getString("huc_twelve_digit_code"));
		station.setDrainageAreaMeasureValue(rs.getString("drainage_area_measure_value"));
		station.setDrainageAreaMeasureUnitCode(rs.getString("drainage_area_measure_unit_code"));
		station.setLatitudeMeasure(rs.getString("latitude_measure"));
		station.setLongitudeMeasure(rs.getString("longitude_measure"));
		station.setHorizontalCollectionMethodName(rs.getString("horizontal_collection_method_name"));
		station.setHorizontalCoordinateReferenceSystemDatumName(rs.getString("horizontal_coordinate_reference_system_datum_name"));
		station.setCountryCode(rs.getString("country_code"));
		station.setStateCode(rs.getString("state_code"));
		station.setCountyCode(rs.getString("county_code"));
		return station;
	}

}
