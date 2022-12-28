package pl.janksiegowy.entity;

import org.springframework.stereotype.Repository;
import pl.janksiegowy.contact.Contact;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class EntityRepositoryImp implements EntityRepository {

    private final SqlEntityRepository repository;

    public EntityRepositoryImp(final SqlEntityRepository repository) {
        this.repository= repository;
    }

    @Override
    public List<Entity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Entity> findEntityByEntityIdAndDate( UUID id, Date date) {
        return repository.findEntityByEntityIdAndDate( id, date);
    }


    @Override
    public Entity save(Entity contact) {
        return repository.save( contact);
    }

    @Override
    public Optional<Entity> findByTaxNumberAndCountryAndType(String taxNumber,
                                                             String country,
                                                             EntityType type) {
        return repository.findByTaxNumberAndCountryAndType( taxNumber, country, type);
    }

}

