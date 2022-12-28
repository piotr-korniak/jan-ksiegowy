package pl.janksiegowy.tenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
@Component
public class SchemaConfig implements BeanPostProcessor {
    private static final String VALID_SCHEMA_NAME_REGEXP= "[A-Za-z0-9_]*";

    @Value( "${multitenancy.main.datasource.hikari.schema}")
    private String schema;

    @Override
    public Object postProcessAfterInitialization( Object bean, String beanName) throws BeansException {

        Optional.ofNullable( schema)
                .filter( s-> !schema.matches( VALID_SCHEMA_NAME_REGEXP)
                            && bean instanceof DataSource)
                .ifPresent( result-> {
                    DataSource dataSource = (DataSource) bean;
                    try (Connection conn= dataSource.getConnection();
                        Statement statement= conn.createStatement()) {
                        log.info( "Going to create DB schema '{}' if not exists.", schema);
                        statement.execute("CREATE SCHEMA IF NOT EXISTS "+ schema);
                    } catch (SQLException e) {
                        throw new RuntimeException( "Failed to create schema '"+ schema + "'", e);
                    }
                });
        return bean;
    }
}
