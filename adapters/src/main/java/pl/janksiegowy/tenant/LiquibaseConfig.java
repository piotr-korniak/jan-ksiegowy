package pl.janksiegowy.tenant;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(
        name= "multitenancy.master.liquibase.enabled",
        havingValue= "true",
        matchIfMissing= true)
@EnableConfigurationProperties( LiquibaseProperties.class)
public class LiquibaseConfig {
    @Bean
    @ConfigurationProperties( "multitenancy.main.liquibase")
    public LiquibaseProperties mainLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties( "multitenancy.tenant.liquibase")
    public LiquibaseProperties tenantLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @ConfigurationProperties( "multitenancy.shard.liquibase")
    public LiquibaseProperties shardLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase mainLiquibase( @LiquibaseDataSource ObjectProvider<DataSource> liquibaseDataSource) {
        System.err.println( "mainLiquibase: "+ liquibaseDataSource) ;
        SpringLiquibase springLiquibase= getSpringLiquibase( mainLiquibaseProperties());
        springLiquibase.setDataSource( liquibaseDataSource.getIfAvailable());
        return springLiquibase;
    }

    public static SpringLiquibase getSpringLiquibase( LiquibaseProperties liquibaseProperties) {
        SpringLiquibase liquibase= new SpringLiquibase();
        liquibase.setChangeLog( liquibaseProperties.getChangeLog());
        liquibase.setContexts( liquibaseProperties.getContexts());
        liquibase.setDefaultSchema( liquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema( liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace( liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable( liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable( liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst( liquibaseProperties.isDropFirst());
        liquibase.setShouldRun( liquibaseProperties.isEnabled());
        liquibase.setLabels( liquibaseProperties.getLabels());
        liquibase.setChangeLogParameters( liquibaseProperties.getParameters());
        liquibase.setRollbackFile( liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate( liquibaseProperties.isTestRollbackOnUpdate());
        return liquibase;

    }

}
