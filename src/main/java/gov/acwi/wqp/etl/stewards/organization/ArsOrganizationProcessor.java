package gov.acwi.wqp.etl.stewards.organization;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.stewards.wqx.WqxOrganization;

public class ArsOrganizationProcessor implements ItemProcessor<WqxOrganization, ArsOrganization> {

	@Override
	public ArsOrganization process(WqxOrganization wqxOrganization) throws Exception {
		ArsOrganization arsOrganization = new ArsOrganization();
		if (null != wqxOrganization.getOrganizationDescription()) {
			arsOrganization.setOrganizationIdentifier(StringUtils.trimToNull(wqxOrganization.getOrganizationDescription().getOrganizationIdentifier()));
			arsOrganization.setOrganizationName(StringUtils.trimToNull(wqxOrganization.getOrganizationDescription().getOrganizationFormalName()));
		}
		if (null != wqxOrganization.getProject()) {
			arsOrganization.setProjectIdentifier(StringUtils.trimToNull(wqxOrganization.getProject().getProjectIdentifier()));
			arsOrganization.setProjectName(StringUtils.trimToNull(wqxOrganization.getProject().getProjectName()));
			arsOrganization.setProjectDescriptionText(StringUtils.trimToNull(wqxOrganization.getProject().getProjectDescriptionText()));
		}
		return arsOrganization;
	}

}
