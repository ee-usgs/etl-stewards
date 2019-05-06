package gov.acwi.wqp.etl.activity;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.EtlConstantUtils;
import gov.acwi.wqp.etl.stewards.result.ArsResult;
import gov.acwi.wqp.etl.stewards.result.ArsResultActivityRowMapper;

@Configuration
public class TransformActivity {

	@Autowired
	@Qualifier("activityProcessor")
	private ItemProcessor<ArsResult, Activity> processor;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSourceWqp;

	@Autowired
	@Qualifier(Application.DATASOURCE_ARS_QUALIFIER)
	private DataSource dataSourceArs;

	@Autowired
	@Qualifier(EtlConstantUtils.SETUP_ACTIVITY_SWAP_TABLE_FLOW)
	private Flow setupActivitySwapTableFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.BUILD_ACTIVITY_INDEXES_FLOW)
	private Flow buildActivityIndexesFlow;

	@Value("classpath:sql/activity/readArsActivity.sql")
	private Resource readerResource;

	@Value("classpath:sql/activity/writeActivity.sql")
	private Resource writerResource;

	@Bean
	public JdbcCursorItemReader<ArsResult> activityReader() throws IOException {
		return new JdbcCursorItemReaderBuilder<ArsResult>()
				.dataSource(dataSourceArs)
				.name("organizationReader")
				.sql(new String(FileCopyUtils.copyToByteArray(readerResource.getInputStream())))
				.rowMapper(new ArsResultActivityRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<Activity> activityWriter() throws IOException {
		JdbcBatchItemWriter<Activity> itemWriter = new JdbcBatchItemWriter<Activity>();
		itemWriter.setDataSource(dataSourceWqp);
		itemWriter.setSql(new String(FileCopyUtils.copyToByteArray(writerResource.getInputStream())));
		ItemSqlParameterSourceProvider<Activity> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformActivityStep() throws IOException {
		return stepBuilderFactory
				.get("transformActivityStep")
				.<ArsResult, Activity>chunk(10000)
				.reader(activityReader())
				.processor(processor)
				.writer(activityWriter())
				.build();
	}

	@Bean
	public Flow activityFlow() throws IOException {
		return new FlowBuilder<SimpleFlow>("activityFlow")
				.start(setupActivitySwapTableFlow)
				.next(transformActivityStep())
				.next(buildActivityIndexesFlow)
				.build();
	}

}
