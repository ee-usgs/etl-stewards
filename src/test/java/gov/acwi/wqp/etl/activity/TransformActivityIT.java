package gov.acwi.wqp.etl.activity;

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
public class TransformActivityIT {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private DataSource dataSource;
	@Value("classpath:db/test_db.sql")
	private Resource resource;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	@Qualifier("activityFlow")
	private Flow activityFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/wqp/activity/activityOld.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/activity/empty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void setupActivityTableStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("setupActivityTableStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/activity/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/projectData/projectData.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/station/station.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsResult.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformActivityStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformActivityStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityActivityIdIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityActivityIdIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityActivityIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityActivityIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	public void buildActivityCountryIndexTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityCountryIndexStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityCountyIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityCountyIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityEventDateIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityEventDateIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityGeomIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityGeomIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityHuc10IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityHuc10IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityHuc12IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityHuc12IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityHuc2IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityHuc2IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityHuc4IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityHuc4IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityHuc6IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityHuc6IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityHuc8IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityHuc8IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityOrganizationIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityOrganizationIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivitySampleMediaIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivitySampleMediaIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivitySiteIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivitySiteIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivitySiteTypeIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivitySiteTypeIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityStateIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityStateIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildActivityStationIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildActivityStationIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/activity/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/projectData/projectData.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/station/station.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsResult.xml")
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	@ExpectedDatabase(value="classpath:/testResult/wqp/activity/activity.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void activityFlowTest() {
		Job activityFlowTest = jobBuilderFactory.get("activityFlowTest")
					.start(activityFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(activityFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob();
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
