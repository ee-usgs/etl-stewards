package gov.acwi.wqp.etl.orgData.domain;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.extract.domain.ArsOrganization;

public class OrgData {

	public OrgData() {}

	public OrgData(ArsOrganization organization) {
		this.dataSourceId = Application.DATA_SOURCE_ID;
		this.dataSource = Application.DATA_SOURCE;
		this.organizationId = Application.ORGANIZATION_ID;
		if (null != organization) {
			this.organization = organization.getOrganizationIdentifier();
			this.organizationName = organization.getOrganizationName();
		}
	}

	private Integer dataSourceId;
	private String dataSource;
	private Integer organizationId;
	private String organization;
	private String organizationName;

	public Integer getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

}
