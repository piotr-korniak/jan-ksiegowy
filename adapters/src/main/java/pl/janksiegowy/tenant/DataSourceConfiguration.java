package pl.janksiegowy.tenant;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties( "multitenancy.main.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @LiquibaseDataSource
    @ConfigurationProperties( "multitenancy.main.datasource.hikari")
    public DataSource mainDataSource() {
        HikariDataSource dataSource= dataSourceProperties()
                .initializeDataSourceBuilder()
                .type( HikariDataSource.class)
                .build();
        dataSource.setPoolName( "mainDataSource");
        //dataSource.setSchema( "jan");
        System.err.println( "mainDataSource -> DataSourceConfiguration");
        return dataSource;
    }
}
