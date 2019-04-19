package gov.acwi.wqp.etl.result;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
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

import gov.acwi.wqp.etl.stewards.result.ArsResult;
import gov.acwi.wqp.etl.stewards.result.ArsResultRowMapper;

@Configuration
public class TransformResult {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("dataSourceWqp")
	private DataSource dataSourceWqp;

	@Autowired
	@Qualifier("dataSourceArs")
	private DataSource dataSourceArs;

	@Autowired
	@Qualifier("setupResultSwapTableFlow")
	private Flow setupResultSwapTableFlow;

	@Autowired
	@Qualifier("buildResultIndexesFlow")
	private Flow buildResultIndexesFlow;

	@Value("classpath:sql/result/readArsResult.sql")
	private Resource readerResource;

	@Value("classpath:sql/result/writeResult.sql")
	private Resource writerResource;

	@Bean
	public JdbcCursorItemReader<ArsResult> resultReader() throws IOException {
		return new JdbcCursorItemReaderBuilder<ArsResult>()
				.dataSource(dataSourceArs)
				.name("organizationReader")
				.sql(new String(FileCopyUtils.copyToByteArray(readerResource.getInputStream())))
				.rowMapper(new ArsResultRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<Result> resultWriter() throws IOException {
		JdbcBatchItemWriter<Result> itemWriter = new JdbcBatchItemWriter<Result>();
		itemWriter.setDataSource(dataSourceWqp);
		itemWriter.setSql(new String(FileCopyUtils.copyToByteArray(writerResource.getInputStream())));
		ItemSqlParameterSourceProvider<Result> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformResultStep() throws IOException {
		return stepBuilderFactory
				.get("transformResultStep")
				.<ArsResult, Result>chunk(10)
				.reader(resultReader())
				.processor(new ResultProcessor())
				.writer(resultWriter())
				.build();
	}

	@Bean
	public Flow resultFlow() throws IOException {
		return new FlowBuilder<SimpleFlow>("resultFlow")
				.start(setupResultSwapTableFlow)
				.next(transformResultStep())
				.next(buildResultIndexesFlow)
				.build();
	}

}
