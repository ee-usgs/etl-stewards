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

	@Autowired
	@Qualifier("resultFlow")
	private Flow resultFlow;

	@Autowired
	@Qualifier("resDetectQntLimitFlow")
	private Flow resDetectQntLimitFlow;

	@Autowired
	@Qualifier("createSummariesFlow")
	private Flow createSummariesFlow;

	@Autowired
	@Qualifier("createLookupCodesFlow")
	private Flow createLookupCodesFlow;

	@Autowired
	@Qualifier("databaseFinalizeFlow")
	private Flow databaseFinalizeFlow;

	@Bean
	public Job wqxEtl() {
		return jobBuilderFactory.get("WQP_ARS_STEWARDS_ETL")
				.start(arsExtractFlow)
				.next(orgDataFlow)
				.next(projectDataFlow)
				.next(monitoringLocationFlow)
				.next(activityFlow)
				.next(resultFlow)
				.next(resDetectQntLimitFlow)
				.next(createSummariesFlow)
				.next(createLookupCodesFlow)
				.next(databaseFinalizeFlow)
				.build()
				.build();
	}

}
