package gov.acwi.wqp.etl.orgData;

import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.orgData.OrgData;
import gov.acwi.wqp.etl.stewards.ArsOrganization;

public class OrgDataProcessor implements ItemProcessor<ArsOrganization, OrgData>{

	@Override
	public OrgData process(ArsOrganization arsOrganization) throws Exception {
		OrgData orgData = new OrgData();
		orgData.setDataSourceId(Application.DATA_SOURCE_ID);
		orgData.setDataSource(Application.DATA_SOURCE);
		orgData.setOrganizationId(Application.ORGANIZATION_ID);
		if (null != arsOrganization) {
			orgData.setOrganization(arsOrganization.getOrganizationIdentifier());
			orgData.setOrganizationName(arsOrganization.getOrganizationName());
		}
		return orgData;
	}

}
