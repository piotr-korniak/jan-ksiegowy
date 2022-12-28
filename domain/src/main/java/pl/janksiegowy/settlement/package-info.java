@TypeDef( defaultForType= SettlementId.class, typeClass= SettlementIdType.class)
@GenericGenerator( name= "settlement-id-generator", strategy= "pl.janksiegowy.base.hibernate.UUIDDomainObjectIdGenerator")

package pl.janksiegowy.settlement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import pl.janksiegowy.base.hibernate.SettlementIdType;
