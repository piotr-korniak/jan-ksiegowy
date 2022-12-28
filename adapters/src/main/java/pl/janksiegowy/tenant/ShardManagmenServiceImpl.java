package pl.janksiegowy.tenant;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.janksiegowy.shard.ShardJpa;
import pl.janksiegowy.shard.ShardRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Service
@EnableConfigurationProperties( LiquibaseProperties.class)
public class ShardManagmenServiceImpl implements ShardManagmenService {

    private static final String VALID_DATABASE_NAME_REGEXP= "[A-Za-z0-9_]*";

    private final ResourceLoader resourceLoader;
    private final JdbcTemplate jdbcTemplate;

    private final ShardRepository shardRepository;

    @Qualifier( "shardLiquibaseProperties")
    private final LiquibaseProperties liquibaseProperties;

    private final PasswordEncoder encoder;

    @Value( "${multitenancy.shard.datasource.url-prefix}")
    private String urlPrefix;

    public ShardManagmenServiceImpl( ResourceLoader resourceLoader,
                                     JdbcTemplate jdbcTemplate,
                                     @Qualifier( "shardLiquibaseProperties") LiquibaseProperties liquibaseProperties,
                                     ShardRepository shardRepository,
                                     PasswordEncoder encoder) {
        this.resourceLoader= resourceLoader;
        this.jdbcTemplate= jdbcTemplate;
        this.liquibaseProperties= liquibaseProperties;
        this.shardRepository= shardRepository;
        this.encoder= encoder;
    }

    @Override
    public void createShard( String tenantId, String db, String password) {

        if (!db.matches( VALID_DATABASE_NAME_REGEXP)) {
            throw new RuntimeException( "Invalid db name: " + db);
        }

        shardRepository.findByName( db)
                        .ifPresent( s->{ throw new RuntimeException( "Shard '"+ db+ "' already exists");});

        String url = urlPrefix+db;
        String encryptedPassword= encoder.encode( password);
        //String encryptedPassword = encryptionService.encrypt(password, secret, salt);

        try {
            createDatabase( db, password);
        } catch( DataAccessException e) {
            throw new RuntimeException( "Error when creating db: " + db, e);
        }
        try( Connection connection= DriverManager.getConnection( url, db, password)) {
            runLiquibase( new SingleConnectionDataSource( connection, false));
        } catch( SQLException | LiquibaseException e) {
            throw new RuntimeException( "Error when populating db: ", e);
        }

        ShardJpa shard= ShardJpa.builder()
                .name(db)
                .build();
        shardRepository.save( shard);
    }

    private void createDatabase( String db, String password) {
        jdbcTemplate.execute( (StatementCallback<Boolean>) stmt ->
                stmt.execute("CREATE DATABASE "+ db));
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt ->
                stmt.execute("CREATE USER "+ db+ " WITH ENCRYPTED PASSWORD '"+ password+ "'"));
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt ->
                stmt.execute("GRANT ALL PRIVILEGES ON DATABASE "+ db+ " TO "+ db));
   }

    private void runLiquibase( DataSource dataSource) throws LiquibaseException {
        SpringLiquibase springLiquibase= LiquibaseConfig.getSpringLiquibase( liquibaseProperties);
        springLiquibase.setDataSource( dataSource);
        springLiquibase.setResourceLoader( resourceLoader);
        springLiquibase.afterPropertiesSet();
    }
}
