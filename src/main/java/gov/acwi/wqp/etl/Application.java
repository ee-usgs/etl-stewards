package gov.acwi.wqp.etl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("default")
public class Application implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static final String JOB_ID = "jobId";
	public static final Integer DATA_SOURCE_ID = 1;
	public static final String DATA_SOURCE = "STEWARDS";
	public static final Integer ORGANIZATION_ID = 3000000;

	@Autowired
	private Job job;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JobExplorer jobExplorer;

	public static void main(String[] args) {
		LOG.info(args.toString());
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		JobParameters parameters = new JobParametersBuilder(jobExplorer)
//				.addDate(JOB_ID, new Date(), true)
//				.toJobParameters();
//		JobExecution jobExecution = jobLauncher.run(job, parameters);
//		if (null == jobExecution 
//				|| ExitStatus.UNKNOWN.getExitCode().contentEquals(jobExecution.getExitStatus().getExitCode())
//				|| ExitStatus.FAILED.getExitCode().contentEquals(jobExecution.getExitStatus().getExitCode())
//				|| ExitStatus.STOPPED.getExitCode().contentEquals(jobExecution.getExitStatus().getExitCode())) {
//			throw new RuntimeException("Job did not complete as planned.");
//		}
	}

}
