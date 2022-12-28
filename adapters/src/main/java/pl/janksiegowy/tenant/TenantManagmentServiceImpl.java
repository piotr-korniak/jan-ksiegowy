package pl.janksiegowy.tenant;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
@EnableConfigurationProperties( LiquibaseProperties.class)
public class TenantManagmentServiceImpl {

    private static final String VALID_SCHEMA_NAME_REGEXP= "[A-Za-z0-9_]*";

    //private final DataSource dataSource;
    private final ResourceLoader resourceLoader;
    private final JdbcTemplate jdbcTemplate;

    @Qualifier( "tenantLiquibaseProperties")
    private final LiquibaseProperties liquibaseProperties;

    @Value( "${multitenancy.shard.datasource.url-prefix}")
    private String urlPrefix;


    public TenantManagmentServiceImpl(
            DataSource dataSource,
            ResourceLoader resourceLoader,
            JdbcTemplate jdbcTemplate,
            @Qualifier( "tenantLiquibaseProperties") LiquibaseProperties liquibaseProperties){

        //this.dataSource= dataSource;
        this.resourceLoader = resourceLoader;
        this.jdbcTemplate = jdbcTemplate;
        this.liquibaseProperties = liquibaseProperties;

    }

    public void createCompany ( String schema) {
        // Verify schema string to prevent SQL injection
        if (!schema.matches(VALID_SCHEMA_NAME_REGEXP)) {
            throw new RuntimeException("Invalid schema name: " + schema);
        }

        try {
            String shardId= TenantContext.getShardId();
            Connection connection=
                    DriverManager.getConnection( urlPrefix+ shardId, shardId, "Sylwia70");
            DataSource dataSource= new SingleConnectionDataSource(connection, false);

            createSchema( dataSource, schema);

            connection.setSchema( schema);
            runLiquibase( dataSource);
        } catch ( SQLException| DataAccessException e) {
            throw new RuntimeException( "Error when creating schema: " + schema, e);
        } catch ( LiquibaseException e) {
            throw new RuntimeException( "Error when populating schema: ", e);
        }

/*        Tenant tenant = Tenant.builder()
                .tenantId(tenantId)
                .schema(schema)
                .build();
        tenantRepository.save(tenant);
*/    }

    private void createSchema( DataSource shardDataSource, String schema) {
        jdbcTemplate.setDataSource( shardDataSource);
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA " + schema));
    }

    private void runLiquibase( DataSource dataSource) throws LiquibaseException {
        SpringLiquibase liquibase= getSpringLiquibase( dataSource);
        liquibase.afterPropertiesSet();
    }

    protected SpringLiquibase getSpringLiquibase( DataSource dataSource) {
        SpringLiquibase liquibase= new SpringLiquibase();
        liquibase.setResourceLoader( resourceLoader);
        liquibase.setDataSource( dataSource);
       //liquibase.setDefaultSchema( schema);
        liquibase.setChangeLog( liquibaseProperties.getChangeLog());
        liquibase.setContexts( liquibaseProperties.getContexts());

        return liquibase;
    }
}
