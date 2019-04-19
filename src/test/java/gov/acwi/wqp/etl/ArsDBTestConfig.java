package gov.acwi.wqp.etl;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

@TestConfiguration
public class ArsDBTestConfig {

	@Autowired
	@Qualifier(Application.DATASOURCE_ARS_QUALIFIER)
	private DataSource dataSourceArs;

	@Autowired
	private DatabaseConfigBean dbUnitDatabaseConfig;

	@Bean
	public DatabaseDataSourceConnectionFactoryBean ars() throws SQLException {
		DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
		dbUnitDatabaseConnection.setDatabaseConfig(dbUnitDatabaseConfig);
		dbUnitDatabaseConnection.setDataSource(dataSourceArs);
		dbUnitDatabaseConnection.setSchema("ars");
		return dbUnitDatabaseConnection;
	}

}
