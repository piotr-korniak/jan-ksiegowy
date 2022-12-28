package pl.janksiegowy.entity.revenue;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.entity.Entity;

import javax.persistence.DiscriminatorValue;

@javax.persistence.Entity
@DiscriminatorValue( value= "R")
public class Revenue extends Entity {

}
