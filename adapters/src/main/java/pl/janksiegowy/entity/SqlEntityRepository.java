package pl.janksiegowy.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.janksiegowy.contact.Contact;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface SqlEntityRepository extends JpaRepository<Entity, EntityId> {

    @Query( value=
            "FROM Contact M " +
            "LEFT OUTER JOIN Contact P "+
            "ON M.entityId= P.entityId AND (P.date <= :date AND M.date < P.date)"+
            "WHERE M.entityId= :id AND M.date <= :date AND P.date IS NULL")
    Optional<Contact> findContactByEntityIdAndDate( UUID id, Date date);

    Optional<Entity> findEntityByEntityIdAndDate( UUID id, Date date);

    @Query( value=
            "FROM Entity M " +
                    "LEFT OUTER JOIN Contact P "+
                    "ON M.entityId= P.entityId AND M.date < P.date "+
                    "WHERE M.taxNumber= :taxNumber AND M.country= :country AND M.type= :type AND P.date IS NULL")
    Optional<Entity> findByTaxNumberAndCountryAndType(String taxNumber,
                                                      String country,
                                                      EntityType type);
}
