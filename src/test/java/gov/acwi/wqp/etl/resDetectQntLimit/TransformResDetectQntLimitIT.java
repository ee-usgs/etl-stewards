package gov.acwi.wqp.etl.resDetectQntLimit;

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
public class TransformResDetectQntLimitIT {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private DataSource dataSource;
	@Value("classpath:db/test_db.sql")
	private Resource resource;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	@Qualifier("resDetectQntLimitFlow")
	private Flow resDetectQntLimitFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/wqp/resDetectQntLimit/resDetectQntLimitOld.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/empty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void setupResDetectQntLimitTableStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("setupResDetectQntLimitTableStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/resDetectQntLimit/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/result/result.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformResDetectQntLimitStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformResDetectQntLimitStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitActivityIdIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitActivityIdIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitActivityIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitActivityIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	public void buildResDetectQntLimitCountryIndexTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitCountryIndexStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitCountyIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitCountyIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitEventDateIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitEventDateIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitGeomIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitGeomIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitHuc10IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitHuc10IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitHuc12IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitHuc12IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitHuc2IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitHuc2IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitHuc4IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitHuc4IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitHuc6IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitHuc6IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitHuc8IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitHuc8IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitOrganizationIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitOrganizationIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitSampleMediaIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitSampleMediaIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitSiteIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitSiteIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitSiteTypeIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitSiteTypeIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitStateIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitStateIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

//	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildResDetectQntLimitStationIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildResDetectQntLimitStationIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	@DatabaseSetup(value="classpath:/testResult/wqp/resDetectQntLimit/empty.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/activity/activity.xml")
	@DatabaseSetup(value="classpath:/testResult/wqp/result/result.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	@ExpectedDatabase(value="classpath:/testResult/wqp/resDetectQntLimit/resDetectQntLimit.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void resDetectQntLimitFlowTest() {
		Job resDetectQntLimitFlowTest = jobBuilderFactory.get("resDetectQntLimitFlowTest")
					.start(resDetectQntLimitFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(resDetectQntLimitFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob();
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
