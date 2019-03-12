package gov.acwi.wqp.etl.orgData.domain;

import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.extract.domain.ArsOrganization;

public class OrgDataProcessor implements ItemProcessor<ArsOrganization, OrgData>{

	@Override
	public OrgData process(ArsOrganization item) throws Exception {
		return new OrgData(item);
	}

}
