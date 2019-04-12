package gov.acwi.wqp.etl.orgData;

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

import gov.acwi.wqp.etl.stewards.organization.ArsOrganization;
import gov.acwi.wqp.etl.stewards.organization.ArsOrganizationRowMapper;


@Configuration
public class TransformOrgData {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("dataSourceWqp")
	private DataSource dataSourceWqp;

	@Autowired
	@Qualifier("dataSourceArs")
	private DataSource dataSourceArs;

	@Autowired
	@Qualifier("setupOrgDataSwapTableFlow")
	private Flow setupOrgDataSwapTableFlow;

	@Autowired
	@Qualifier("buildOrgDataIndexesFlow")
	private Flow buildOrgDataIndexesFlow;

	@Value("classpath:sql/orgData/readArsOrgProject.sql")
	private Resource readerResource;

	@Value("classpath:sql/orgData/writeOrgData.sql")
	private Resource writerResource;

	@Bean
	public JdbcCursorItemReader<ArsOrganization> wqxOrgReader() throws IOException {
		return new JdbcCursorItemReaderBuilder<ArsOrganization>()
				.dataSource(dataSourceArs)
				.name("organizationReader")
				.sql(new String(FileCopyUtils.copyToByteArray(readerResource.getInputStream())))
				.rowMapper(new ArsOrganizationRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<OrgData> orgDataWriter() throws IOException {
		JdbcBatchItemWriter<OrgData> itemWriter = new JdbcBatchItemWriter<OrgData>();
		itemWriter.setDataSource(dataSourceWqp);
		itemWriter.setSql(new String(FileCopyUtils.copyToByteArray(writerResource.getInputStream())));

		ItemSqlParameterSourceProvider<OrgData> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();

		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformOrgDataStep() throws IOException {
		return stepBuilderFactory
				.get("transformOrgDataStep")
				.<ArsOrganization, OrgData>chunk(10)
				.reader(wqxOrgReader())
				.processor(new OrgDataProcessor())
				.writer(orgDataWriter())
				.build();
	}

	@Bean
	public Flow orgDataFlow() throws IOException {
		return new FlowBuilder<SimpleFlow>("orgDataFlow")
				.start(setupOrgDataSwapTableFlow)
				.next(transformOrgDataStep())
				.next(buildOrgDataIndexesFlow)
				.build();
	}

}
