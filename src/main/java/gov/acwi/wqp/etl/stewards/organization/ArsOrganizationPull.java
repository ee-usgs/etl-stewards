package gov.acwi.wqp.etl.stewards.organization;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
import org.springframework.util.FileCopyUtils;

import gov.acwi.wqp.etl.Application;
import gov.acwi.wqp.etl.stewards.ArsUrlResource;
import gov.acwi.wqp.etl.stewards.wqx.WqxOrganization;
import gov.acwi.wqp.etl.stewards.wqx.WqxOrganizationDescription;
import gov.acwi.wqp.etl.stewards.wqx.WqxProject;

@Component
public class ArsOrganizationPull {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier(Application.DATASOURCE_ARS_QUALIFIER)
	private DataSource dataSourceArs;

	@Value("${wqx.organization:classpath:/testData/xml/wqxOrganization.xml}")
	private URL url;

	@Autowired
	@Qualifier("truncateArsOrgProject")
	private Tasklet truncateArsOrgProject;

	@Value("classpath:sql/orgData/writeArsOrgProject.sql")
	private Resource writerResource;

	@Bean
	public StaxEventItemReader<WqxOrganization> arsOrganizationReader() throws MalformedURLException {
		StaxEventItemReader<WqxOrganization> staxEventItemReader = new StaxEventItemReader<>();
		staxEventItemReader.setResource(new ArsUrlResource(url));
		staxEventItemReader.setFragmentRootElementName("Organization");
		Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
		unMarshaller.setClassesToBeBound(WqxOrganization.class, WqxOrganizationDescription.class, WqxProject.class);
		staxEventItemReader.setUnmarshaller(unMarshaller);
		return staxEventItemReader;
	}

	@Bean
	public ItemWriter<ArsOrganization> arsOrganizationWriter() throws IOException {
		JdbcBatchItemWriter<ArsOrganization> itemWriter = new JdbcBatchItemWriter<ArsOrganization>();
		itemWriter.setDataSource(dataSourceArs);
		itemWriter.setSql(new String(FileCopyUtils.copyToByteArray(writerResource.getInputStream())));
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
	public Step arsOrganizationPullStep() throws IOException {
		return stepBuilderFactory
				.get("arsOrganizationPullStep")
				.<WqxOrganization, ArsOrganization>chunk(10)
				.reader(arsOrganizationReader())
				.processor(new ArsOrganizationProcessor())
				.writer(arsOrganizationWriter())
				.build();
	}

	@Bean
	public Flow arsOrganizationPullFlow() throws IOException {
		return new FlowBuilder<SimpleFlow>("arsOrganizationPullFlow")
				.start(truncateArsOrgProjectStep())
				.next(arsOrganizationPullStep())
				.build();
	}

}
