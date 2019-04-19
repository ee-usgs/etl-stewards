package gov.acwi.wqp.etl.orgData;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.ConfigurationService;
import gov.acwi.wqp.etl.orgData.OrgData;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganization;

@Component
public class OrgDataProcessor implements ItemProcessor<ArsOrganization, OrgData> {

	private final ConfigurationService configurationService;

	@Autowired
	public OrgDataProcessor(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@Override
	public OrgData process(ArsOrganization arsOrganization) throws Exception {
		OrgData orgData = new OrgData();
		orgData.setDataSourceId(configurationService.getEtlDataSourceId());
		orgData.setDataSource(configurationService.getEtlDataSource());
		orgData.setOrganizationId(Application.ORGANIZATION_ID);
		if (null != arsOrganization) {
			orgData.setOrganization(arsOrganization.getOrganizationIdentifier());
			orgData.setOrganizationName(arsOrganization.getOrganizationName());
		}
		return orgData;
	}

}
