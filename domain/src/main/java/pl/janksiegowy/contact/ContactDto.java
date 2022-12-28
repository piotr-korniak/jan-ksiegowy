package pl.janksiegowy.contact;

import lombok.Builder;
import lombok.Getter;
import pl.janksiegowy.entity.EntityId;
import pl.janksiegowy.entity.EntityType;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Getter
@Builder( builderMethodName= "")
public class ContactDto {

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

    public ContactDto( EntityId id, UUID entityId, String taxNumber, EntityType type, Date date, String name,
                       String address, String postcode, String town, String country,
                       boolean supplier, boolean customer) {
        this.id= id;
        this.entityId= entityId;
        this.taxNumber= taxNumber;
        this.type= type;
        this.date= date;
        this.name= name;
        this.address= address;
        this.town= town;
        this.postcode= postcode;
        this.country= country;
        this.supplier= supplier;
        this.customer= customer;
    }

    public static ContactDtoBuilder builder( String type) {
        return new ContactDtoBuilder()
                .type( EntityType.valueOf( type));
    }

}
