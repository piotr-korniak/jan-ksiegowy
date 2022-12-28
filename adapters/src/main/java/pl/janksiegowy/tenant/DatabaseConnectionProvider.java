package pl.janksiegowy.tenant;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseConnectionProvider
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Qualifier( "mainDataSource")
    private DataSource mainDataSource;

   // private LoadingCache<String, DataSource> tenantDataSources;

    @Override
    protected DataSource selectAnyDataSource() {
        System.err.println( "DynamicDataSource -> selectAnyDataSource ");
        return mainDataSource;
    }

    @Override
    protected DataSource selectDataSource( String tenantIdentifier) {
        System.err.println( "DynamicDataSource -> selectDataSource -> "+ tenantIdentifier);

        return null;
    }
}
