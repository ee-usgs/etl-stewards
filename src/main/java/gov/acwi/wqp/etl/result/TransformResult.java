package gov.acwi.wqp.etl.result;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
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
import gov.acwi.wqp.etl.stewards.result.ArsResultRowMapper;

@Configuration
public class TransformResult {

	@Autowired
	@Qualifier("resultProcessor")
	private ItemProcessor<ArsResult, Result> processor;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSourceWqp;

	@Autowired
	@Qualifier(Application.DATASOURCE_ARS_QUALIFIER)
	private DataSource dataSourceArs;

	@Autowired
	@Qualifier(EtlConstantUtils.SETUP_RESULT_SWAP_TABLE_FLOW)
	private Flow setupResultSwapTableFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.BUILD_RESULT_INDEXES_FLOW)
	private Flow buildResultIndexesFlow;

	@Value("classpath:sql/result/readArsResult.sql")
	private Resource readerResource;
//	@Value("classpath:sql/result/selectArsResult.sql")
//	private Resource selectClause;
//	@Value("classpath:sql/result/fromArsResult.sql")
//	private Resource fromClause;
//	@Value("classpath:sql/result/whereArsResult.sql")
//	private Resource whereClause;

	@Value("classpath:sql/result/writeResult.sql")
	private Resource writerResource;

	@Bean
	public JdbcCursorItemReader<ArsResult> resultReader() throws IOException {
		return new JdbcCursorItemReaderBuilder<ArsResult>()
				.dataSource(dataSourceArs)
				.name("organizationReader")
				.sql(new String(FileCopyUtils.copyToByteArray(readerResource.getInputStream())))
				.rowMapper(new ArsResultRowMapper())
				.fetchSize(500)
				.maxRows(5000)
				.build();
	}
//	@Bean
//	public ItemReader<ArsResult> resultReader() throws Exception {
//		SqlPagingQueryProviderFactoryBean providerFactory = new SqlPagingQueryProviderFactoryBean();
//		providerFactory.setDataSource(dataSourceArs);
//		providerFactory.setSelectClause(new String(FileCopyUtils.copyToByteArray(selectClause.getInputStream())));
//		providerFactory.setFromClause(new String(FileCopyUtils.copyToByteArray(fromClause.getInputStream())));
//		providerFactory.setWhereClause(new String(FileCopyUtils.copyToByteArray(whereClause.getInputStream())));
//		providerFactory.setSortKey("result_id");
//
//		return new JdbcPagingItemReaderBuilder<ArsResult>()
//				.dataSource(dataSourceArs)
//				.name("organizationReader")
//				.pageSize(5000)
//				.rowMapper(new ArsResultRowMapper())
//				.queryProvider(providerFactory.getObject())
//				.build();
//	}

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
	public Step transformResultStep() throws Exception {
		return stepBuilderFactory
				.get("transformResultStep")
				.<ArsResult, Result>chunk(10000)
				.reader(resultReader())
				.processor(processor)
				.writer(resultWriter())
				.build();
	}

	@Bean
	public Flow resultFlow() throws Exception {
		return new FlowBuilder<SimpleFlow>("resultFlow")
				.start(setupResultSwapTableFlow)
				.next(transformResultStep())
				.next(buildResultIndexesFlow)
				.build();
	}

}
