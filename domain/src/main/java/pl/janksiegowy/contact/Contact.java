package pl.janksiegowy.contact;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.entity.Entity;

import javax.persistence.DiscriminatorValue;

@javax.persistence.Entity
@DiscriminatorValue( value= "C")
@Getter
@Setter
@Accessors( chain= true)
public class Contact extends Entity {

    private boolean supplier;
    private boolean customer;


}
