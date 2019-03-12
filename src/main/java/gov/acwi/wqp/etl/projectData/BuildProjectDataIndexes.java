package gov.acwi.wqp.etl.projectData;

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
public class BuildProjectDataIndexes {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("buildProjectDataOrganizationIndex")
	private Tasklet buildProjectDataOrganizationIndex;

	@Autowired
	@Qualifier("buildProjectDataIdentifierIndex")
	private Tasklet buildProjectDataIdentifierIndex;

	@Bean
	public Step buildProjectDataOrganizationIndexStep() {
		return stepBuilderFactory.get("buildProjectDataOrganizationIndexStep")
				.tasklet(buildProjectDataOrganizationIndex)
				.build();
	}

	@Bean
	public Step buildProjectDataIdentifierIndexStep() {
		return stepBuilderFactory.get("buildProjectDataIdentifierIndexStep")
				.tasklet(buildProjectDataIdentifierIndex)
				.build();
	}

	@Bean
	public Flow buildProjectDataIndexesFlow() {
		return new FlowBuilder<SimpleFlow>("buildProjectDataIndexesFlow")
				.start(buildProjectDataOrganizationIndexStep())
				.next(buildProjectDataIdentifierIndexStep())
				.build();
	}

}
