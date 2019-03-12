package gov.acwi.wqp.etl.projectData;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gov.acwi.wqp.etl.extract.domain.ArsOrganization;
import gov.acwi.wqp.etl.projectData.domain.ProjectData;
import gov.acwi.wqp.etl.projectData.domain.ProjectDataProcessor;


@Configuration
public class ProjectDataTransformation {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("setupProjectDataTable")
	private Tasklet setupProjectDataTable;

	@Autowired
	@Qualifier("buildProjectDataIndexesFlow")
	private Flow buildProjectDataIndexesFlow;

	@Autowired
	@Qualifier("wqxOrgReader")
	private ItemReader<ArsOrganization> wqxOrgReader;
//	@Bean
//	public JdbcCursorItemReader<Organization> wqxOrgReader() {
//		return new JdbcCursorItemReaderBuilder<Organization>()
//				.dataSource(this.dataSource)
//				.name("organizationReader")
//				.sql("select * from ars_org_project")
//				.rowMapper(new OrganizationRowMapper())
//				.build();
//	}

	@Bean
	public ItemWriter<ProjectData> projectDataWriter() {
		JdbcBatchItemWriter<ProjectData> itemWriter = new JdbcBatchItemWriter<ProjectData>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql("insert "
				+ " into project_data_swap_stewards (data_source_id, data_source, organization, organization_name, project_identifier, project_name, description)"
				+ " values (:dataSourceId, :dataSource, :organization, :organizationName, :projectIdentifier, :projectName, :description)");

		ItemSqlParameterSourceProvider<ProjectData> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();

		itemWriter.setItemSqlParameterSourceProvider(paramProvider);
		return itemWriter;
	}

	@Bean
	public Step setupProjectDataTableStep() {
		return stepBuilderFactory.get("setupProjectDataTableStep")
				.tasklet(setupProjectDataTable)
				.build();
	}

	@Bean
	public Step transformProjectDataStep() {
		return stepBuilderFactory
				.get("transformProjectDataStep")
				.<ArsOrganization, ProjectData>chunk(10)
				.reader(wqxOrgReader)
				.processor(new ProjectDataProcessor())
				.writer(projectDataWriter())
				.build();
	}

	@Bean
	public Flow projectDataFlow() {
		return new FlowBuilder<SimpleFlow>("projectDataFlow")
				.start(setupProjectDataTableStep())
				.next(transformProjectDataStep())
				.next(buildProjectDataIndexesFlow)
				.build();
	}

}
