package gov.acwi.wqp.etl.activity;

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

//@Configuration
public class BuildActivityIndexes {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("buildActivityCountryIndex")
	private Tasklet buildActivityCountryIndex;

	@Autowired
	@Qualifier("buildActivityCountyIndex")
	private Tasklet buildActivityCountyIndex;

	@Autowired
	@Qualifier("buildActivityGeomIndex")
	private Tasklet buildActivityGeomIndex;

	@Autowired
	@Qualifier("buildActivityHuc10Index")
	private Tasklet buildActivityHuc10Index;

	@Autowired
	@Qualifier("buildActivityHuc12Index")
	private Tasklet buildActivityHuc12Index;

	@Autowired
	@Qualifier("buildActivityHuc2Index")
	private Tasklet buildActivityHuc2Index;

	@Autowired
	@Qualifier("buildActivityHuc4Index")
	private Tasklet buildActivityHuc4Index;

	@Autowired
	@Qualifier("buildActivityHuc6Index")
	private Tasklet buildActivityHuc6Index;

	@Autowired
	@Qualifier("buildActivityHuc8Index")
	private Tasklet buildActivityHuc8Index;

	@Autowired
	@Qualifier("buildActivityOrganizationIndex")
	private Tasklet buildActivityOrganizationIndex;

	@Autowired
	@Qualifier("buildActivitySiteIndex")
	private Tasklet buildActivitySiteIndex;

	@Autowired
	@Qualifier("buildActivitySiteTypeIndex")
	private Tasklet buildActivitySiteTypeIndex;

	@Autowired
	@Qualifier("buildActivityStateIndex")
	private Tasklet buildActivityStateIndex;

	@Autowired
	@Qualifier("buildActivityStationIndex")
	private Tasklet buildActivityStationIndex;


	@Bean
	public Step buildActivityCountryIndexStep() {
		return stepBuilderFactory.get("buildActivityCountryIndexStep")
				.tasklet(buildActivityCountryIndex)
				.build();
	}

	@Bean
	public Step buildActivityCountyIndexStep() {
		return stepBuilderFactory.get("buildActivityCountyIndexStep")
				.tasklet(buildActivityCountyIndex)
				.build();
	}

	@Bean
	public Step buildActivityGeomIndexStep() {
		return stepBuilderFactory.get("buildActivityGeomIndexStep")
				.tasklet(buildActivityGeomIndex)
				.build();
	}

	@Bean
	public Step buildActivityHuc10IndexStep() {
		return stepBuilderFactory.get("buildActivityHuc10IndexStep")
				.tasklet(buildActivityHuc10Index)
				.build();
	}

	@Bean
	public Step buildActivityHuc12IndexStep() {
		return stepBuilderFactory.get("buildActivityHuc12IndexStep")
				.tasklet(buildActivityHuc12Index)
				.build();
	}

	@Bean
	public Step buildActivityHuc2IndexStep() {
		return stepBuilderFactory.get("buildActivityHuc2IndexStep")
				.tasklet(buildActivityHuc2Index)
				.build();
	}

	@Bean
	public Step buildActivityHuc4IndexStep() {
		return stepBuilderFactory.get("buildActivityHuc4IndexStep")
				.tasklet(buildActivityHuc4Index)
				.build();
	}

	@Bean
	public Step buildActivityHuc6IndexStep() {
		return stepBuilderFactory.get("buildActivityHuc6IndexStep")
				.tasklet(buildActivityHuc6Index)
				.build();
	}

	@Bean
	public Step buildActivityHuc8IndexStep() {
		return stepBuilderFactory.get("buildActivityHuc8IndexStep")
				.tasklet(buildActivityHuc8Index)
				.build();
	}

	@Bean
	public Step buildActivityOrganizationIndexStep() {
		return stepBuilderFactory.get("buildActivityOrganizationIndexStep")
				.tasklet(buildActivityOrganizationIndex)
				.build();
	}

	@Bean
	public Step buildActivitySiteIndexStep() {
		return stepBuilderFactory.get("buildActivitySiteIndexStep")
				.tasklet(buildActivitySiteIndex)
				.build();
	}

	@Bean
	public Step buildActivitySiteTypeIndexStep() {
		return stepBuilderFactory.get("buildActivitySiteTypeIndexStep")
				.tasklet(buildActivitySiteTypeIndex)
				.build();
	}

	@Bean
	public Step buildActivityStateIndexStep() {
		return stepBuilderFactory.get("buildActivityStateIndexStep")
				.tasklet(buildActivityStateIndex)
				.build();
	}

	@Bean
	public Step buildActivityStationIndexStep() {
		return stepBuilderFactory.get("buildActivityStationIndexStep")
				.tasklet(buildActivityStationIndex)
				.build();
	}


	@Bean
	public Flow buildActivityIndexesFlow() {
		return new FlowBuilder<SimpleFlow>("buildActivityIndexesFlow")
				.start(buildActivityCountryIndexStep())
				.next(buildActivityCountyIndexStep())
				.next(buildActivityGeomIndexStep())
				.next(buildActivityHuc10IndexStep())
				.next(buildActivityHuc12IndexStep())
				.next(buildActivityHuc2IndexStep())
				.next(buildActivityHuc4IndexStep())
				.next(buildActivityHuc6IndexStep())
				.next(buildActivityHuc8IndexStep())
				.next(buildActivityOrganizationIndexStep())
				.next(buildActivitySiteIndexStep())
				.next(buildActivitySiteTypeIndexStep())
				.next(buildActivityStateIndexStep())
				.next(buildActivityStationIndexStep())
				.build();
	}

}
