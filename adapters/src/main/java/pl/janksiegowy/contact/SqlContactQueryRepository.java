package pl.janksiegowy.contact;

import liquibase.pro.packaged.O;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pl.janksiegowy.entity.EntityId;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@org.springframework.stereotype.Repository
interface SqlContactQueryRepository extends ContactQueryRepository, Repository<Contact, EntityId> {

    @Override
    @Query( value= "SELECT new pl.janksiegowy.contact.ContactDto( " +
            "M.id, M.entityId, M.taxNumber, M.type, M.date, M.name," +
            "M.address, M.postcode, M.town, M.country, M.supplier, M.customer) " +
            "FROM Contact M " +
            "LEFT OUTER JOIN Contact P "+
            "ON M.entityId= P.entityId AND (P.date <= :date AND M.date < P.date)"+
            "WHERE M.entityId= :id AND M.date <= :date AND P.date IS NULL")
    Optional<ContactDto> findByContactIdAndDate( UUID id, Date date);

    @Override
    @Query( value= "SELECT new pl.janksiegowy.contact.ContactDto( " +
            "M.id, M.entityId, M.taxNumber, M.type, M.date, M.name, " +
            "M.address, M.postcode, M.town, M.country, M.supplier, M.customer) " +
            "FROM Contact M " +
            "LEFT OUTER JOIN Contact P "+
            "ON M.entityId= P.entityId AND M.date < P.date "+
            "WHERE M.taxNumber= :taxNumber AND M.country= :country AND P.date IS NULL")
    Optional<ContactDto> findByTaxNumberAndCountry( String taxNumber, String country);
}