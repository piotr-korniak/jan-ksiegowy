package pl.janksiegowy.tenant;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages= { "pl.janksiegowy.metric",
                        "pl.janksiegowy.entity",
                        "pl.janksiegowy.contact",
                        "pl.janksiegowy.settlement",
                        "pl.janksiegowy.settlement.invoice"},
        entityManagerFactoryRef = "tenantEntityManagerFactory",
        transactionManagerRef = "tenantTransactionManager"
)
@EnableConfigurationProperties( JpaProperties.class)
public class TenantPersistenceConfig {

    private final ConfigurableListableBeanFactory beanFactory;
    private final JpaProperties jpaProperties;
    private final String entityPackages;

    @Autowired
    public TenantPersistenceConfig(
            ConfigurableListableBeanFactory beanFactory,
            JpaProperties jpaProperties,
            @Value( "pl.janksiegowy.metric") String entityPackages) {
        this.beanFactory= beanFactory;
        this.jpaProperties= jpaProperties;
        this.entityPackages = entityPackages;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
            @Qualifier( "schemaConnectionProvider")
                MultiTenantConnectionProvider connectionProvider,
            @Qualifier( "currentTenantIdentifierResolver")
                CurrentTenantIdentifierResolver tenantResolver) {
        System.err.println( ">> tenant EntityManagerFactory");

        LocalContainerEntityManagerFactoryBean emf= new LocalContainerEntityManagerFactoryBean();

        emf.setPersistenceUnitName( "tenant-persistence-unit");
        emf.setPackagesToScan(  "pl.janksiegowy.metric",
                                "pl.janksiegowy.entity",
                                "pl.janksiegowy.contact",
                                "pl.janksiegowy.settlement",
                                "pl.janksiegowy.settlement.invoice");
        emf.setJpaVendorAdapter( new HibernateJpaVendorAdapter());

        Map<String, Object> properties= new HashMap<>( jpaProperties.getProperties());

        properties.put( AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put( AvailableSettings.IMPLICIT_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put( AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer( beanFactory));

        properties.remove( AvailableSettings.DEFAULT_SCHEMA);
        properties.put( AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        properties.put( AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
        properties.put( AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);

        emf.setJpaPropertyMap( properties);
        
        return emf;
    }


    @Bean
    @Primary
    public JpaTransactionManager tenantTransactionManager(
            @Qualifier( "tenantEntityManagerFactory") EntityManagerFactory emf  ) {

        JpaTransactionManager tenantTransactionManager= new JpaTransactionManager();
        tenantTransactionManager.setEntityManagerFactory( emf);
        return tenantTransactionManager;
    }

}
