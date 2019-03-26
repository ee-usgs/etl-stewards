package gov.acwi.wqp.etl.stewards;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArsOrganizationRowMapper implements RowMapper<ArsOrganization> {

	public static final String ORGANIZATION_COLUMN_NAME = "organization";
	public static final String ORGANIZATION_NAME_COLUMN_NAME = "organization_name";
	public static final String PROJECT_IDENTIFIER_COLUMN_NAME = "project_identifier";
	public static final String PROJECT_NAME_COLUMN_NAME = "project_name";
	public static final String PROJECT_DESCRIPTION_TEXT_COLUMN_NAME = "project_description_text";

	@Override
	public ArsOrganization mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArsOrganization organization = new ArsOrganization();
		organization.setOrganizationIdentifier(rs.getString(ORGANIZATION_COLUMN_NAME));
		organization.setOrganizationName(rs.getString(ORGANIZATION_NAME_COLUMN_NAME));
		organization.setProjectIdentifier(rs.getString(PROJECT_IDENTIFIER_COLUMN_NAME));
		organization.setProjectName(rs.getString(PROJECT_NAME_COLUMN_NAME));
		organization.setProjectDescriptionText(rs.getString(PROJECT_DESCRIPTION_TEXT_COLUMN_NAME));
		return organization;
	}

}
