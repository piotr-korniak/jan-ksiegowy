package pl.janksiegowy.entity.dto;

import lombok.Builder;
import lombok.Getter;
import pl.janksiegowy.entity.EntityId;
import pl.janksiegowy.entity.EntityType;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder( builderMethodName= "")
public class EntityDto {
    private EntityId id;
    private UUID entityId;
    private String taxNumber;
    private EntityType type;
    private Date date;
    private String name;
    private String address;
    private String postcode;
    private String town;
    private String country;
    private boolean supplier;
    private boolean customer;

    public boolean noEntityId() {
        return entityId== null;
    }

    public static EntityDtoBuilder builder( EntityType type, Date date) {
        return new EntityDtoBuilder()
                .type( type)
                .date( date);
    }

}
