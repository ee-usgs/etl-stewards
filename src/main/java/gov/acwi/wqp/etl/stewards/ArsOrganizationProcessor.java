package gov.acwi.wqp.etl.stewards;

import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.extract.domain.WqxOrganization;

public class ArsOrganizationProcessor implements ItemProcessor<WqxOrganization, ArsOrganization>{

	@Override
	public ArsOrganization process(WqxOrganization item) throws Exception {
		return new ArsOrganization(item);
	}

}
