package gov.acwi.wqp.etl;

import org.junit.Before;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.context.annotation.Import;

import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@Import({DBTestConfig.class, ArsDBTestConfig.class})
@DbUnitConfiguration(databaseConnection={"wqp","ars","nwis","pg"})
public abstract class ArsBaseFlowIT extends BaseFlowIT {

	@Before
	@Override
	public void baseSetup() {
		testJobParameters= new JobParametersBuilder()
				.addJobParameters(jobLauncherTestUtils.getUniqueJobParameters())
				.addString(EtlConstants.JOB_PARM_DATA_SOURCE_ID, Application.DATA_SOURCE_ID.toString(), true)
				.addString(EtlConstants.JOB_PARM_DATA_SOURCE, Application.DATA_SOURCE.toLowerCase(), true)
				.addString(EtlConstants.JOB_PARM_SCHEMA, EtlConstants.WQP_SCHEMA_NAME, false)
				.addString(EtlConstants.JOB_PARM_GEO_SCHEMA, EtlConstants.NWIS_SCHEMA_NAME, false)
				.toJobParameters();
	}

}
