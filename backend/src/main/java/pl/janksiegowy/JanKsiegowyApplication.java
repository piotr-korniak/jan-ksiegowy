package pl.janksiegowy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;


@SpringBootApplication( exclude={
        DataSourceAutoConfiguration.class,
        LiquibaseAutoConfiguration.class })
public class JanKsiegowyApplication {

    public static void main( String[] args) {
        SpringApplication.run( JanKsiegowyApplication.class, args);
    }

}
