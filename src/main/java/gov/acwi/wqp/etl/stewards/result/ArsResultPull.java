package gov.acwi.wqp.etl.stewards.result;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import gov.acwi.wqp.etl.stewards.ArsUrlResource;
import gov.acwi.wqp.etl.stewards.wqx.WqxActivity;
import gov.acwi.wqp.etl.stewards.wqx.WqxActivityDescription;
import gov.acwi.wqp.etl.stewards.wqx.WqxCollectionMethod;
import gov.acwi.wqp.etl.stewards.wqx.WqxDataQuality;
import gov.acwi.wqp.etl.stewards.wqx.WqxDetectionQuantitationLimitMeasure;
import gov.acwi.wqp.etl.stewards.wqx.WqxResult;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultAnalyticalMethod;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultDescription;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultDetectionQuantitationLimit;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultLabInformation;
import gov.acwi.wqp.etl.stewards.wqx.WqxResultMeasure;
import gov.acwi.wqp.etl.stewards.wqx.WqxSampleDescription;
import gov.acwi.wqp.etl.stewards.wqx.WqxTime;


@Configuration
public class ArsResultPull {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("dataSourceArs")
	private DataSource dataSourceArs;

	@Value("${wqx.result}")
	private List<String> paths;

	@Autowired
	@Qualifier("truncateArsResult")
	private Tasklet truncateArsResult;

	@Value("classpath:sql/result/writeArsResult.sql")
	private Resource writerResource;

	@Bean
	@StepScope
	public StaxEventItemReader<WqxActivity> arsResultReader(@Value("#{stepExecutionContext[url]}") URL url) throws MalformedURLException {
		StaxEventItemReader<WqxActivity> staxEventItemReader = new StaxEventItemReader<>();
		staxEventItemReader.setResource(new ArsUrlResource(url));
		staxEventItemReader.setFragmentRootElementName("Activity");
		Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
		unMarshaller.setClassesToBeBound(WqxActivity.class, WqxActivityDescription.class,
				WqxTime.class, WqxSampleDescription.class, WqxCollectionMethod.class,
				WqxResult.class, WqxResultDescription.class, WqxResultMeasure.class,
				WqxDataQuality.class, WqxResultAnalyticalMethod.class, WqxDetectionQuantitationLimitMeasure.class,
				WqxResultLabInformation.class, WqxResultDetectionQuantitationLimit.class
				);
		staxEventItemReader.setUnmarshaller(unMarshaller);
		return staxEventItemReader;
	}

	@Bean
	public ItemWriter<ArsResult> arsResultWriter() throws IOException {
		JdbcBatchItemWriter<ArsResult> itemWriter = new JdbcBatchItemWriter<ArsResult>();
		itemWriter.setDataSource(dataSourceArs);
		itemWriter.setSql(new String(FileCopyUtils.copyToByteArray(writerResource.getInputStream())));

		ItemSqlParameterSourceProvider<ArsResult> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();

		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step truncateArsResultStep() {
		return stepBuilderFactory
				.get("truncateArsResultStep")
				.tasklet(truncateArsResult)
				.build();
	}

	@Bean
	public CustomMultiResourcePartitioner partitioner() throws FileNotFoundException, MalformedURLException {
		CustomMultiResourcePartitioner partitioner = new CustomMultiResourcePartitioner();
		List<URL> urls = new ArrayList<>();
		for (String path : paths) {
			if (path.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
				urls.add(ResourceUtils.getURL(path));
			} else {
				urls.add(new URL(path));
			}
		}
		partitioner.setUrls(urls);
		return partitioner;
	}

	@Bean
	public Step arsResultPartitionStep() throws IOException {
		return stepBuilderFactory.get("arsResultPartitionStep").<WqxActivity, ArsResult>chunk(10000)
				.reader(arsResultReader(null))
				.processor(new ArsResultProcessor())
				.writer(arsResultWriter())
				.build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(5);
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setQueueCapacity(5);
		taskExecutor.setKeepAliveSeconds(6000);
		taskExecutor.afterPropertiesSet();
		return taskExecutor;
	}

	@Bean
	public Step arsResultPullStep() throws IOException {
		return stepBuilderFactory.get("arsResultPullStep")
				.partitioner("arsResultPartitionStep", partitioner())
				.step(arsResultPartitionStep())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Flow arsResultPullFlow() throws IOException {
		return new FlowBuilder<SimpleFlow>("arsResultPullFlow")
				.start(truncateArsResultStep())
				.next(arsResultPullStep())
				.build();
	}

}
