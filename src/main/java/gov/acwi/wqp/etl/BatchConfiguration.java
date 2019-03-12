package gov.acwi.wqp.etl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	@Qualifier("arsExtractFlow")
	private Flow arsExtractFlow;

//	@Autowired
//	@Qualifier("databaseSetupFlow")
//	private Flow databaseSetupFlow;

	@Autowired
	@Qualifier("orgDataFlow")
	private Flow orgDataFlow;

	@Autowired
	@Qualifier("projectDataFlow")
	private Flow projectDataFlow;

	@Autowired
	@Qualifier("monitoringLocationFlow")
	private Flow monitoringLocationFlow;

	@Autowired
	@Qualifier("activityFlow")
	private Flow activityFlow;

	@Bean
	public Job wqxEtl() {
		return jobBuilderFactory.get("WQP_ARS_STEWARDS_ETL")
//				.incrementer(jobIncrementer)
				.start(arsExtractFlow)
//				.start(databaseSetupFlow)
				.next(orgDataFlow)
				.next(projectDataFlow)
				.next(monitoringLocationFlow)
//				.next(biologicalHabitatMetricFlow)
//				.next(monitoringLocationObjectFlow)
				.next(activityFlow)
//				.next(activityObjectFlow)
//				.next(activityMetricFlow)
//				.next(resultFlow)
//				.next(resultObjectFlow)
//				.next(resDetectQntLmtFlow)
//				.next(projectMlWeightingFlow)
//				.next(createSummariesFlow)
//				.next(createCodesFlow)
//				.next(databaseFinalizeFlow)
				.build()
				.build();
	}

}
