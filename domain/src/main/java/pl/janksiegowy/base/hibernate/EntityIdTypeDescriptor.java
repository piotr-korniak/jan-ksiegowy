package pl.janksiegowy.base.hibernate;

import pl.janksiegowy.entity.EntityId;

public class EntityIdTypeDescriptor extends NumericDomainObjectIdTypeDescriptor<EntityId> {

    public EntityIdTypeDescriptor() {
        super( EntityId.class, EntityId::new);
    }

    public static final EntityIdTypeDescriptor INSTANCE= new EntityIdTypeDescriptor();
}
