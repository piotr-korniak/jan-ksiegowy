package pl.janksiegowy.entity.shareholder;

import pl.janksiegowy.entity.Entity;

import javax.persistence.DiscriminatorValue;

@javax.persistence.Entity
@DiscriminatorValue( value= "S")
public class Shareholders extends Entity {

}
