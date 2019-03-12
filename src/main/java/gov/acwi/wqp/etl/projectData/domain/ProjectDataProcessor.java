package gov.acwi.wqp.etl.projectData.domain;

import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.extract.domain.ArsOrganization;

public class ProjectDataProcessor implements ItemProcessor<ArsOrganization, ProjectData>{

	@Override
	public ProjectData process(ArsOrganization item) throws Exception {
		return new ProjectData(item);
	}

}
