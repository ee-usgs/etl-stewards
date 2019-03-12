package gov.acwi.wqp.etl.projectData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBatchTest
@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	StepScopeTestExecutionListener.class,
	DbUnitTestExecutionListener.class
	})
@AutoConfigureTestDatabase(replace=Replace.AUTO_CONFIGURED)
public class ProjectDataTransformationIT {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private DataSource dataSource;
	@Value("classpath:db/test_db.sql")
	private Resource resource;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	@Qualifier("projectDataFlow")
	private Flow projectDataFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/wqp/projectData/projectDataOld.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/projectData/empty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void setupProjectDataTableStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("setupProjectDataTableStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/ars/arsOrgProject.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformProjectDataStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformProjectDataStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	public void buildProjectDataOrganizationIndexTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildProjectDataOrganizationIndexStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildProjectDataIdentifierIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildProjectDataIdentifierIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	@DatabaseSetup(value="classpath:/testData/wqp/projectData/projectDataOld.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsOrgProject.xml")
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	@ExpectedDatabase(value="classpath:/testResult/wqp/projectData/projectData.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void projectDataFlowTest() {
		Job projectDataFlowTest = jobBuilderFactory.get("projectDataFlowTest")
					.start(projectDataFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(projectDataFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob();
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
