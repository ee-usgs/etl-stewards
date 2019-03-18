package gov.acwi.wqp.etl.result;

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
public class TransformResultIT {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private DataSource dataSource;
	@Value("classpath:db/test_db.sql")
	private Resource resource;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	@Qualifier("resultFlow")
	private Flow resultFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/wqp/result/resultOld.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/empty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void setupResultTableStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("setupResultTableStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/result/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/activity/activity.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsResult.xml")
	@DatabaseSetup(value="classpath:/testData/ars/arsCharNameToType.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformResultStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformResultStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultActivityIdIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultActivityIdIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultActivityIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultActivityIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	public void buildResultCountryIndexTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultCountryIndexStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultCountyIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultCountyIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultEventDateIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultEventDateIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultGeomIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultGeomIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultHuc10IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultHuc10IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultHuc12IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultHuc12IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultHuc2IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultHuc2IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultHuc4IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultHuc4IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultHuc6IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultHuc6IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultHuc8IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultHuc8IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultOrganizationIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultOrganizationIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultSampleMediaIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultSampleMediaIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultSiteIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultSiteIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultSiteTypeIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultSiteTypeIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultStateIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultStateIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResultStationIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResultStationIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/result/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/activity/activity.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsResult.xml")
	@DatabaseSetup(value="classpath:/testData/ars/arsCharNameToType.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	@ExpectedDatabase(value="classpath:/testResult/wqp/result/result.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void resultFlowTest() {
		Job resultFlowTest = jobBuilderFactory.get("resultFlowTest")
					.start(resultFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(resultFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob();
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
