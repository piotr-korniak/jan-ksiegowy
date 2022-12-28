package pl.janksiegowy.entity;

import lombok.AllArgsConstructor;
import pl.janksiegowy.contact.Contact;
import pl.janksiegowy.entity.dto.EntityDto;
import pl.janksiegowy.entity.revenue.Revenue;
import pl.janksiegowy.entity.shareholder.Shareholders;

import java.util.UUID;

@AllArgsConstructor
public class EntityFacade  {

    private final EntityFactory factory;
    private final EntityRepository repository;

    public EntityDto save( EntityDto source) {

        if( source.noEntityId()) {
            return factory.to( repository   // New Entity
                    .save( factory.from( source).setEntityId( UUID.randomUUID())));
        }

        return factory.to( repository.findEntityByEntityIdAndDate( source.getEntityId(), source.getDate())
                .map( entity-> {            // Update Entity history
                    return repository.save( factory.update( entity, source));
                })
                .orElseGet( ()-> {          // New Entity history
                    return repository.save( factory.from( source));
                })
        );

    }

}
