package gov.acwi.wqp.etl.stewards.organization;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArsOrganizationRowMapper implements RowMapper<ArsOrganization> {

	public static final String ORGANIZATION = "organization";
	public static final String ORGANIZATION_NAME = "organization_name";
	public static final String PROJECT_IDENTIFIER = "project_identifier";
	public static final String PROJECT_NAME = "project_name";
	public static final String PROJECT_DESCRIPTION_TEXT = "project_description_text";

	@Override
	public ArsOrganization mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsOrganization organization = new ArsOrganization();
		organization.setOrganizationIdentifier(rs.getString(ORGANIZATION));
		organization.setOrganizationName(rs.getString(ORGANIZATION_NAME));
		organization.setProjectIdentifier(rs.getString(PROJECT_IDENTIFIER));
		organization.setProjectName(rs.getString(PROJECT_NAME));
		organization.setProjectDescriptionText(rs.getString(PROJECT_DESCRIPTION_TEXT));
		return organization;
	}

}
