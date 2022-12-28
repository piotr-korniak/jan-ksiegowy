package pl.janksiegowy.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.base.AggregateRoot;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Table( name= "ENTITIES")
@javax.persistence.Entity
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
@Setter
@Accessors( chain= true)
public abstract class Entity extends AggregateRoot<EntityId> implements Serializable {

    @Id
    @GeneratedValue( generator= "entity-id-generator")
    private EntityId id;
    private UUID entityId; // for history and query

    @Column( insertable = false, updatable = false)
    @Enumerated( EnumType.STRING)
    private EntityType type;

    private Date date;
    @Column( name= "TAX_NUMBER")
    private String taxNumber;

    private String name;
    private String address;
    private String postcode;
    private String town;
    private String country;

    public boolean isSupplier() {
        return false;
    }

    public Entity setSupplier( boolean supplier) {
        return this;
    };

    public boolean isCustomer() {
        return false;
    }
    public Entity setCustomer( boolean customer) {
        return this;
    };

}
