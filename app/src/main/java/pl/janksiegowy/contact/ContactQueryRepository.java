package pl.janksiegowy.contact;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactQueryRepository {

    Optional<ContactDto> findByContactIdAndDate( UUID id, Date date);
    <T> List<T> findBy( Class<T> type);

    <T> List<T> findByTaxNumber(Class<T> type, String taxNuber);
    Optional<ContactDto> findByTaxNumberAndCountry( String taxNumber, String country);

}
