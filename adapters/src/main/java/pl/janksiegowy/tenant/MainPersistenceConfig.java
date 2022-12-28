package pl.janksiegowy.tenant;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages= { "pl.janksiegowy.user", "pl.janksiegowy.shard"},
        entityManagerFactoryRef= "mainEntityManagerFactory",
        transactionManagerRef= "mainTransactionManager"
)
@EnableConfigurationProperties( {DataSourceProperties.class, JpaProperties.class})
public class MainPersistenceConfig {
    private final ConfigurableListableBeanFactory beanFactory;
    private final JpaProperties jpaProperties;
    private final String entityPackages;

    @Autowired
    public MainPersistenceConfig( ConfigurableListableBeanFactory beanFactory,
                                   JpaProperties jpaProperties,
                                   @Value( "pl.janksiegowy.user")
                                   String entityPackages) {
        this.beanFactory= beanFactory;
        this.jpaProperties= jpaProperties;
        this.entityPackages= entityPackages;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
            @Qualifier( "mainDataSource") DataSource dataSource) {
        System.err.println( ">> main EntityManagerFactory");
        LocalContainerEntityManagerFactoryBean em= new LocalContainerEntityManagerFactoryBean();

        em.setPersistenceUnitName( "main-persistence-unit");
        em.setPackagesToScan( "pl.janksiegowy.user",
                              "pl.janksiegowy.shard");
        em.setDataSource( dataSource);

        em.setJpaVendorAdapter( new HibernateJpaVendorAdapter());

        Map<String, Object> properties= new HashMap<>( jpaProperties.getProperties());
        properties.put( AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put( AvailableSettings.IMPLICIT_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put( AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer( beanFactory));
        em.setJpaPropertyMap( properties);

        return em;
    }

    @Bean
    public JpaTransactionManager mainTransactionManager(
            @Qualifier( "mainEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager= new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( emf);
        return transactionManager;
    }
}
