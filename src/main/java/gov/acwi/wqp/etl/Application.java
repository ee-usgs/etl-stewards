package gov.acwi.wqp.etl;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static final Integer DATA_SOURCE_ID = 1;
	public static final String DATA_SOURCE = "STEWARDS";
	public static final Integer ORGANIZATION_ID = 3000000;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public static final DateTimeFormatter ARS_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M/d/y");

}
