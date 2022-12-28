package pl.janksiegowy.entity;

import pl.janksiegowy.contact.Contact;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository {

    List<Entity> findAll();

    Optional<Entity> findEntityByEntityIdAndDate( UUID id, Date date);

    Entity save( Entity entity);

    Optional<Entity> findByTaxNumberAndCountryAndType(String taxNumber,
                                                      String country,
                                                      EntityType type);
}
