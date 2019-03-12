package gov.acwi.wqp.etl.monitoringLocation;

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
public class MonitoringLocationTransformationIT {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private DataSource dataSource;
	@Value("classpath:db/test_db.sql")
	private Resource resource;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	@Qualifier("monitoringLocationFlow")
	private Flow monitoringLocationFlow;

	@PostConstruct
	public void beforeClass() throws ScriptException, SQLException {
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/wqp/station/stationOld.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/station/empty.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void setupMonitoringLocationTableStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("setupMonitoringLocationTableStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	@DatabaseSetup(value="classpath:/testData/ars/arsSiteTypeToPrimary.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsOrgProject.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsStation.xml")
	@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void transformMonitoringLocationStepTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("transformMonitoringLocationStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	public void buildMonitoringLocationCountryIndexTest() {
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationCountryIndexStep");
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationCountyIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationCountyIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationGeomIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationGeomIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationHuc10IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationHuc10IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationHuc12IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationHuc12IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationHuc2IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationHuc2IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationHuc4IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationHuc4IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationHuc6IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationHuc6IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationHuc8IndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationHuc8IndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationOrganizationIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationOrganizationIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationSiteIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationSiteIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationSiteTypeIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationSiteTypeIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationStateIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationStateIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	//TODO activate in PostgreSQL database testing
//		@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
		public void buildMonitoringLocationStationIndexTest() {
			try {
				JobExecution jobExecution = jobLauncherTestUtils.launchStep("buildMonitoringLocationStationIndexStep");
				assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getLocalizedMessage());
			}
		}

	@Test
	@DatabaseSetup(value="classpath:/testData/ars/arsSiteTypeToPrimary.xml")
	@DatabaseSetup(value="classpath:/testData/wqp/station/stationOld.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsOrgProject.xml")
	@DatabaseSetup(value="classpath:/testResult/ars/arsStation.xml")
//TODO activate in PostgreSQL database testing
//	@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED, table="pg_indexes", query="select tablename, indexname from pg_indexes where tablename='project_data_swap_stewards' and indexname='project_data_stewards_projectanization'")
	@ExpectedDatabase(value="classpath:/testResult/wqp/station/station.xml", assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
	public void monitoringLocationFlowTest() {
		Job monitoringLocationFlowTest = jobBuilderFactory.get("monitoringLocationFlowTest")
					.start(monitoringLocationFlow)
					.build()
					.build();
		jobLauncherTestUtils.setJob(monitoringLocationFlowTest);
		try {
			JobExecution jobExecution = jobLauncherTestUtils.launchJob();
			assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
