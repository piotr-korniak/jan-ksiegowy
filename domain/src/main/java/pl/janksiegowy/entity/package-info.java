@TypeDef( defaultForType= EntityId.class, typeClass= EntityIdType.class)
@GenericGenerator( name= "entity-id-generator", strategy= "org.hibernate.id.IdentityGenerator")
//@GenericGenerator( name= "contact-id-generator", strategy= "org.hibernate.id.UUIDGenerator")
//@GenericGenerator( name= "contact-id-generator", strategy= "pl.janksiegowy.base.hibernate.ContactIdGenerator")
//@GenericGenerator( name= "contact-id-generator", strategy= "pl.janksiegowy.base.hibernate.UUIDDomainObjectIdGenerator")

package pl.janksiegowy.entity;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.GenericGenerator;
import pl.janksiegowy.base.hibernate.EntityIdType;
