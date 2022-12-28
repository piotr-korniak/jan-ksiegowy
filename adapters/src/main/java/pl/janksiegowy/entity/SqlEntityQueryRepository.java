package pl.janksiegowy.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.entity.dto.EntityQueryDto;

import java.util.Optional;

@org.springframework.stereotype.Repository
interface SqlEntityQueryRepository extends EntityQueryRepository, Repository<Entity, EntityId>{

    @Override
    @Query( value= "SELECT M " +
                   "FROM Entity M " +
                   "LEFT OUTER JOIN Contact P "+
                   "ON M.entityId= P.entityId AND M.date < P.date "+
                   "WHERE M.taxNumber= :taxNumber AND M.country= :country AND M.type= :type AND P.date IS NULL")
    Optional<EntityQueryDto> findByTaxNumberAndCountryAndType( String taxNumber, String country, EntityType type);
}
