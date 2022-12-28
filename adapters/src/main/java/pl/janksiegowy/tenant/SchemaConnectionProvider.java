package pl.janksiegowy.tenant;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class SchemaConnectionProvider implements MultiTenantConnectionProvider {

    @Qualifier( "mainDataSource")
    private final DataSource mainDataSource;

    @Qualifier( "mainDataSourceProperties")
    private final DataSourceProperties dataSourceProperties;

    @Value( "${multitenancy.tenant.datasource.url-prefix}")
    private String urlPrefix;

    private LoadingCache<String, DataSource> tenantDataSources;

    @PostConstruct
    private void createCache() {
        tenantDataSources = Caffeine.newBuilder()
                .build( tenantId-> {
                    return createAndConfigureDataSource( tenantId);
                });
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        System.err.println( "SchemaConnectionProvider (getAnyConnection) ");
        return mainDataSource.getConnection();
        //return null;
    }

    @Override
    public void releaseAnyConnection( Connection connection) throws SQLException {
        //mainDataSource.getConnection().close();
    }

    @Override
    public Connection getConnection( String tenantId) throws SQLException {
        System.err.println( "getConnection (tenant): "+ tenantId);
        System.err.println( "SchemaConnectionProvider (shard): "+ TenantContext.getShardId());

        Connection connecion= tenantDataSources.get( TenantContext.getShardId())
                                                .getConnection();

        connecion.setSchema( tenantId);
        return connecion;
    }

    @Override
    public void releaseConnection( String companyId, Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    private DataSource createAndConfigureDataSource( String tenant) {
        System.err.println( "createAndConfigureDataSource (url): "+ urlPrefix+ tenant);

        HikariDataSource ds= dataSourceProperties.initializeDataSourceBuilder()
                .type( HikariDataSource.class).build();

        ds.setUsername( tenant);
        ds.setPassword( "Sylwia70");
        ds.setJdbcUrl( urlPrefix+ tenant);

        ds.setPoolName( tenant); //tenant.getTenantId() + TENANT_POOL_NAME_SUFFIX);

        return ds;
    }
}
