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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = { "pl.janksiegowy.company"},
        entityManagerFactoryRef = "shardEntityManagerFactory",
        transactionManagerRef = "shardTransactionManager"
)
@EnableConfigurationProperties( JpaProperties.class)
public class ShardPersistenceConfig {

    private final ConfigurableListableBeanFactory beanFactory;
    private final JpaProperties jpaProperties;
 //   private final String entityPackages;

    @Autowired
    public ShardPersistenceConfig(
            ConfigurableListableBeanFactory beanFactory,
            JpaProperties jpaProperties){
//            @Value( "pl.janksiegowy.company, pl.janksiegowy.shard") String entityPackages) {
        this.beanFactory= beanFactory;
        this.jpaProperties= jpaProperties;
 //       this.entityPackages = entityPackages;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean shardEntityManagerFactory(
            //@Qualifier( "shardDataSource") DataSource dataSource)
            @Qualifier("schemaConnectionProvider") MultiTenantConnectionProvider connectionProvider,
            @Qualifier("shardIdentifierResolver") CurrentTenantIdentifierResolver tenantResolver)
            {

        System.err.println( ">> shard EntityManagerFactory");

        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();

        emfBean.setPersistenceUnitName( "shard-persistence-unit");
        emfBean.setPackagesToScan( "pl.janksiegowy.company");

        JpaVendorAdapter vendorAdapter= new HibernateJpaVendorAdapter();
        emfBean.setJpaVendorAdapter( vendorAdapter);

        Map<String, Object> properties = new HashMap<>(this.jpaProperties.getProperties());
        properties.put( AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put( AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put( AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer(this.beanFactory));
        properties.put( AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        properties.put( AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
        properties.put( AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);
        emfBean.setJpaPropertyMap(properties);

        return emfBean;
    }

    @Bean
    public JpaTransactionManager shardTransactionManager(
            @Qualifier( "shardEntityManagerFactory") EntityManagerFactory emf  ) {

        JpaTransactionManager tenantTransactionManager= new JpaTransactionManager();
        tenantTransactionManager.setEntityManagerFactory( emf);
        return tenantTransactionManager;
    }
}
