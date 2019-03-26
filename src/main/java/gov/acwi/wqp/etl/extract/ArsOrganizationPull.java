package gov.acwi.wqp.etl.extract;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import gov.acwi.wqp.etl.extract.domain.WqxOrganization;
import gov.acwi.wqp.etl.extract.domain.WqxOrganizationDescription;
import gov.acwi.wqp.etl.extract.domain.WqxProject;
import gov.acwi.wqp.etl.stewards.ArsOrganization;
import gov.acwi.wqp.etl.stewards.ArsOrganizationProcessor;

@Component
public class ArsOrganizationPull {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Value("classpath:testData/xml/wqxOrganization.xml")
	private Resource resource;

	@Autowired
	@Qualifier("truncateArsOrgProject")
	private Tasklet truncateArsOrgProject;

	@Bean
	public StaxEventItemReader<WqxOrganization> arsOrganizationReader() {
		StaxEventItemReader<WqxOrganization> staxEventItemReader = new StaxEventItemReader<>();
//		try {
//			//This is a state with no data at this time. It will give us back just the Organization and Project data.
//			staxEventItemReader.setResource(new UrlResource("https://www.nrrig.mwa.ars.usda.gov/st40_wqp/service1.svc/station?countrycode=us&statecode=US%3A56"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		staxEventItemReader.setResource(resource);
		staxEventItemReader.setFragmentRootElementName("Organization");
		Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
		unMarshaller.setClassesToBeBound(WqxOrganization.class, WqxOrganizationDescription.class, WqxProject.class);
		staxEventItemReader.setUnmarshaller(unMarshaller);
		return staxEventItemReader;
	}

	@Bean
	public ItemWriter<ArsOrganization> arsOrganizationWriter() {
		JdbcBatchItemWriter<ArsOrganization> itemWriter = new JdbcBatchItemWriter<ArsOrganization>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql("insert "
				+ " into ars_org_project (organization, organization_name, project_identifier, project_name, project_description_text)"
				+ " values (:organizationIdentifier, :organizationName, :projectIdentifier, :projectName, :projectDescriptionText)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ArsOrganization>());
		itemWriter.afterPropertiesSet();
		return itemWriter;
	}

	@Bean
	public Step truncateArsOrgProjectStep() {
		return stepBuilderFactory
				.get("truncateArsOrgProjectStep")
				.tasklet(truncateArsOrgProject)
				.build();
	}

	@Bean
	public Step arsOrganizationPullStep() {
		return stepBuilderFactory
				.get("arsOrganizationPullStep")
				.<WqxOrganization, ArsOrganization>chunk(10)
				.reader(arsOrganizationReader())
				.processor(new ArsOrganizationProcessor())
				.writer(arsOrganizationWriter())
				.build();
	}

	@Bean
	public Flow arsOrganizationPullFlow() {
		return new FlowBuilder<SimpleFlow>("arsOrganizationPullFlow")
				.start(truncateArsOrgProjectStep())
				.next(arsOrganizationPullStep())
				.build();
	}

}
