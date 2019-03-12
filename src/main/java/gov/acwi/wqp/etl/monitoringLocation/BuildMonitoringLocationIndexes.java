package gov.acwi.wqp.etl.monitoringLocation;

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
public class BuildMonitoringLocationIndexes {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("buildMonitoringLocationCountryIndex")
	private Tasklet buildMonitoringLocationCountryIndex;

	@Autowired
	@Qualifier("buildMonitoringLocationCountyIndex")
	private Tasklet buildMonitoringLocationCountyIndex;

	@Autowired
	@Qualifier("buildMonitoringLocationGeomIndex")
	private Tasklet buildMonitoringLocationGeomIndex;

	@Autowired
	@Qualifier("buildMonitoringLocationHuc10Index")
	private Tasklet buildMonitoringLocationHuc10Index;

	@Autowired
	@Qualifier("buildMonitoringLocationHuc12Index")
	private Tasklet buildMonitoringLocationHuc12Index;

	@Autowired
	@Qualifier("buildMonitoringLocationHuc2Index")
	private Tasklet buildMonitoringLocationHuc2Index;

	@Autowired
	@Qualifier("buildMonitoringLocationHuc4Index")
	private Tasklet buildMonitoringLocationHuc4Index;

	@Autowired
	@Qualifier("buildMonitoringLocationHuc6Index")
	private Tasklet buildMonitoringLocationHuc6Index;

	@Autowired
	@Qualifier("buildMonitoringLocationHuc8Index")
	private Tasklet buildMonitoringLocationHuc8Index;

	@Autowired
	@Qualifier("buildMonitoringLocationOrganizationIndex")
	private Tasklet buildMonitoringLocationOrganizationIndex;

	@Autowired
	@Qualifier("buildMonitoringLocationSiteIndex")
	private Tasklet buildMonitoringLocationSiteIndex;

	@Autowired
	@Qualifier("buildMonitoringLocationSiteTypeIndex")
	private Tasklet buildMonitoringLocationSiteTypeIndex;

	@Autowired
	@Qualifier("buildMonitoringLocationStateIndex")
	private Tasklet buildMonitoringLocationStateIndex;

	@Autowired
	@Qualifier("buildMonitoringLocationStationIndex")
	private Tasklet buildMonitoringLocationStationIndex;


	@Bean
	public Step buildMonitoringLocationCountryIndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationCountryIndexStep")
				.tasklet(buildMonitoringLocationCountryIndex)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationCountyIndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationCountyIndexStep")
				.tasklet(buildMonitoringLocationCountyIndex)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationGeomIndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationGeomIndexStep")
				.tasklet(buildMonitoringLocationGeomIndex)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationHuc10IndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationHuc10IndexStep")
				.tasklet(buildMonitoringLocationHuc10Index)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationHuc12IndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationHuc12IndexStep")
				.tasklet(buildMonitoringLocationHuc12Index)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationHuc2IndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationHuc2IndexStep")
				.tasklet(buildMonitoringLocationHuc2Index)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationHuc4IndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationHuc4IndexStep")
				.tasklet(buildMonitoringLocationHuc4Index)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationHuc6IndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationHuc6IndexStep")
				.tasklet(buildMonitoringLocationHuc6Index)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationHuc8IndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationHuc8IndexStep")
				.tasklet(buildMonitoringLocationHuc8Index)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationOrganizationIndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationOrganizationIndexStep")
				.tasklet(buildMonitoringLocationOrganizationIndex)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationSiteIndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationSiteIndexStep")
				.tasklet(buildMonitoringLocationSiteIndex)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationSiteTypeIndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationSiteTypeIndexStep")
				.tasklet(buildMonitoringLocationSiteTypeIndex)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationStateIndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationStateIndexStep")
				.tasklet(buildMonitoringLocationStateIndex)
				.build();
	}

	@Bean
	public Step buildMonitoringLocationStationIndexStep() {
		return stepBuilderFactory.get("buildMonitoringLocationStationIndexStep")
				.tasklet(buildMonitoringLocationStationIndex)
				.build();
	}


	@Bean
	public Flow buildMonitoringLocationIndexesFlow() {
		return new FlowBuilder<SimpleFlow>("buildMonitoringLocationIndexesFlow")
				.start(buildMonitoringLocationCountryIndexStep())
				.next(buildMonitoringLocationCountyIndexStep())
				.next(buildMonitoringLocationGeomIndexStep())
				.next(buildMonitoringLocationHuc10IndexStep())
				.next(buildMonitoringLocationHuc12IndexStep())
				.next(buildMonitoringLocationHuc2IndexStep())
				.next(buildMonitoringLocationHuc4IndexStep())
				.next(buildMonitoringLocationHuc6IndexStep())
				.next(buildMonitoringLocationHuc8IndexStep())
				.next(buildMonitoringLocationOrganizationIndexStep())
				.next(buildMonitoringLocationSiteIndexStep())
				.next(buildMonitoringLocationSiteTypeIndexStep())
				.next(buildMonitoringLocationStateIndexStep())
				.next(buildMonitoringLocationStationIndexStep())
				.build();
	}

}
