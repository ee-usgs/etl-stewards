package gov.acwi.wqp.etl.projectData;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import gov.acwi.wqp.etl.EtlConstantUtils;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganization;


@Configuration
public class TransformProjectData {

	@Autowired
	@Qualifier("projectDataProcessor")
	private ItemProcessor<ArsOrganization, ProjectData> processor;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSourceWqp;

	@Autowired
	@Qualifier(EtlConstantUtils.SETUP_PROJECT_DATA_SWAP_TABLE_FLOW)
	private Flow setupProjectDataSwapTableFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.BUILD_PROJECT_DATA_INDEXES_FLOW)
	private Flow buildProjectDataIndexesFlow;

	@Autowired
	@Qualifier(EtlConstantUtils.ANALYZE_PROJECT_DATA_FLOW)
	private Flow analyzeProjectDataFlow;

	@Autowired
	@Qualifier("wqxOrgReader")
	private ItemReader<ArsOrganization> wqxOrgReader;

	@Value("classpath:sql/projectData/writeProjectData.sql")
	private Resource writerResource;

	@Bean
	public ItemWriter<ProjectData> projectDataWriter() throws IOException {
		JdbcBatchItemWriter<ProjectData> itemWriter = new JdbcBatchItemWriter<ProjectData>();
		itemWriter.setDataSource(dataSourceWqp);
		itemWriter.setSql(new String(FileCopyUtils.copyToByteArray(writerResource.getInputStream())));

		ItemSqlParameterSourceProvider<ProjectData> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();

		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformProjectDataStep() throws IOException {
		return stepBuilderFactory
				.get("transformProjectDataStep")
				.<ArsOrganization, ProjectData>chunk(10)
				.reader(wqxOrgReader)
				.processor(processor)
				.writer(projectDataWriter())
				.build();
	}

	@Bean
	public Flow projectDataFlow() throws IOException {
		return new FlowBuilder<SimpleFlow>("projectDataFlow")
				.start(setupProjectDataSwapTableFlow)
				.next(transformProjectDataStep())
				.next(buildProjectDataIndexesFlow)
				.next(analyzeProjectDataFlow)
				.build();
	}

}
