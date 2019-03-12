package gov.acwi.wqp.etl.orgData;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildOrgDataIndexes {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("buildOrgDataOrganizationIndex")
	private Tasklet buildOrgDataOrganizationIndex;

	@Bean
	public Step buildOrgDataOrganizationIndexStep() {
		return stepBuilderFactory.get("buildOrgDataOrganizationIndexStep")
				.tasklet(buildOrgDataOrganizationIndex)
				.build();
	}

	@Bean
	public Flow buildOrgDataIndexesFlow() {
		return new FlowBuilder<SimpleFlow>("buildOrgDataIndexesFlow")
				.start(buildOrgDataOrganizationIndexStep())
				.build();
	}

}
