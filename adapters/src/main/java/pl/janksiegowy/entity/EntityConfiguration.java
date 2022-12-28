package pl.janksiegowy.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityConfiguration {

    @Bean
    EntityFacade entityFacade( final EntityRepository repository) {
        return new EntityFacade( new EntityFactory(), repository);
    }
}
