package gov.acwi.wqp.etl.orgData.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.acwi.wqp.etl.extract.domain.ArsOrganization;

public class OrganizationRowMapper implements RowMapper<ArsOrganization> {

	@Override
	public ArsOrganization mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsOrganization organization = new ArsOrganization();
		organization.setOrganizationIdentifier(rs.getString("organization"));
		organization.setOrganizationName(rs.getString("organization_name"));
		organization.setProjectIdentifier(rs.getString("project_identifier"));
		organization.setProjectName(rs.getString("project_name"));
		organization.setProjectDescriptionText(rs.getString("project_description_text"));
		return organization;
	}

}
