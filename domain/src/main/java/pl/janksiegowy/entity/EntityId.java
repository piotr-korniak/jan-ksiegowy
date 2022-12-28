package pl.janksiegowy.entity;

import lombok.Setter;
import pl.janksiegowy.base.support.NumericDomainObjectId;

@Setter
public class EntityId extends NumericDomainObjectId {

    public EntityId(long id) {
        super( id);
    }

}
