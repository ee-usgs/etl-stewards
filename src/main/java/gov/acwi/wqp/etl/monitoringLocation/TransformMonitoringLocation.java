package gov.acwi.wqp.etl.monitoringLocation;

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
import gov.acwi.wqp.etl.stewards.monitoringLocation.ArsMonitoringLocation;
import gov.acwi.wqp.etl.stewards.monitoringLocation.ArsMonitoringLocationRowMapper;

@Configuration
public class TransformMonitoringLocation {

	@Autowired
	@Qualifier("monitoringLocationProcessor")
	private ItemProcessor<ArsMonitoringLocation, MonitoringLocation> processor;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSourceWqp;

	@Autowired
	@Qualifier(Application.DATASOURCE_ARS_QUALIFIER)
	private DataSource dataSourceArs;

	@Autowired
	@Qualifier(EtlConstantUtils.SETUP_MONITORING_LOCATION_SWAP_TABLE_FLOW)
	private Flow setupMonitoringLocationSwapTableFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.BUILD_MONITORING_LOCATION_INDEXES_FLOW)
	private Flow buildMonitoringLocationIndexesFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.ANALYZE_MONITORING_LOCATION_FLOW)
	private Flow analyzeMonitoringLocationFlow;

	@Value("classpath:sql/monitoringLocation/readArsMonitoringLocation.sql")
	private Resource readerResource;

	@Value("classpath:sql/monitoringLocation/writeMonitoringLocation.sql")
	private Resource writerResource;

	@Bean
	public JdbcCursorItemReader<ArsMonitoringLocation> monitoringLocationReader() throws IOException {
		return new JdbcCursorItemReaderBuilder<ArsMonitoringLocation>()
				.dataSource(dataSourceArs)
				.name("monitoringLocationReader")
				.sql(new String(FileCopyUtils.copyToByteArray(readerResource.getInputStream())))
				.rowMapper(new ArsMonitoringLocationRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<MonitoringLocation> monitoringLocationWriter() throws IOException {
		JdbcBatchItemWriter<MonitoringLocation> itemWriter = new JdbcBatchItemWriter<MonitoringLocation>();
		itemWriter.setDataSource(dataSourceWqp);
		itemWriter.setSql(new String(FileCopyUtils.copyToByteArray(writerResource.getInputStream())));
		ItemSqlParameterSourceProvider<MonitoringLocation> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformMonitoringLocationStep() throws IOException {
		return stepBuilderFactory
				.get("transformMonitoringLocationStep")
				.<ArsMonitoringLocation, MonitoringLocation>chunk(100)
				.reader(monitoringLocationReader())
				.processor(processor)
				.writer(monitoringLocationWriter())
				.build();
	}

	@Bean
	public Flow monitoringLocationFlow() throws IOException {
		return new FlowBuilder<SimpleFlow>("monitoringLocationFlow")
				.start(setupMonitoringLocationSwapTableFlow)
				.next(transformMonitoringLocationStep())
				.next(buildMonitoringLocationIndexesFlow)
				.next(analyzeMonitoringLocationFlow)
				.build();
	}

}
