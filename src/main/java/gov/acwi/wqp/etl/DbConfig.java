package gov.acwi.wqp.etl;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DbConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.datasource-wqp")
	public DataSourceProperties dataSourcePropertiesWqp() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.datasource-wqp")
	public DataSource dataSourceWqp() {
		return dataSourcePropertiesWqp().initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	public JdbcTemplate jdbcTemplateWqp() {
		return new JdbcTemplate(dataSourceWqp());
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource-ars")
	public DataSourceProperties dataSourcePropertiesArs() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource-ars")
	public DataSource dataSourceArs() {
		return dataSourcePropertiesArs().initializeDataSourceBuilder().build();
	}

	@Bean
	public JdbcTemplate jdbcTemplateArs() {
		return new JdbcTemplate(dataSourceArs());
	}
}
