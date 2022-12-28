package pl.janksiegowy.entity;

import pl.janksiegowy.entity.dto.EntityQueryDto;

import java.util.Optional;

public interface EntityQueryRepository {

    Optional<EntityQueryDto> findByTaxNumberAndCountryAndType( String taxNumber,
                                                               String country,
                                                               EntityType type);
}
