package gov.acwi.wqp.etl.extract.domain;

import org.springframework.batch.item.ItemProcessor;

public class ArsOrganizationProcessor implements ItemProcessor<WqxOrganization, ArsOrganization>{

	@Override
	public ArsOrganization process(WqxOrganization item) throws Exception {
		return new ArsOrganization(item);
	}

}
