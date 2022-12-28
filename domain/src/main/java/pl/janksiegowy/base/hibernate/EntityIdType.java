package pl.janksiegowy.base.hibernate;

import pl.janksiegowy.entity.EntityId;

public class EntityIdType extends NumericDomainObjectIdCustomType<EntityId> {
    public EntityIdType() {
        super( EntityIdTypeDescriptor.INSTANCE);
    }


}
