package gov.acwi.wqp.etl.orgData;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gov.acwi.wqp.etl.stewards.ArsOrganization;
import gov.acwi.wqp.etl.stewards.ArsOrganizationRowMapper;


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

	@Bean
	public JdbcCursorItemReader<ArsOrganization> wqxOrgReader() {
		return new JdbcCursorItemReaderBuilder<ArsOrganization>()
				.dataSource(dataSourceArs)
				.name("organizationReader")
				.sql("select * from org_project")
				.rowMapper(new ArsOrganizationRowMapper())
				.build();
	}

	@Bean
	public ItemWriter<OrgData> orgDataWriter() {
		JdbcBatchItemWriter<OrgData> itemWriter = new JdbcBatchItemWriter<OrgData>();
		itemWriter.setDataSource(dataSourceWqp);
		itemWriter.setSql("insert into org_data_swap_stewards (data_source_id, data_source, organization_id, organization, organization_name)"
				+ " values (:dataSourceId, :dataSource, :organizationId, :organization, :organizationName)");

		ItemSqlParameterSourceProvider<OrgData> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();

		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step transformOrgDataStep() {
		return stepBuilderFactory
				.get("transformOrgDataStep")
				.<ArsOrganization, OrgData>chunk(10)
				.reader(wqxOrgReader())
				.processor(new OrgDataProcessor())
				.writer(orgDataWriter())
				.build();
	}

	@Bean
	public Flow orgDataFlow() {
		return new FlowBuilder<SimpleFlow>("orgDataFlow")
				.start(setupOrgDataSwapTableFlow)
				.next(transformOrgDataStep())
				.next(buildOrgDataIndexesFlow)
				.build();
	}

}
