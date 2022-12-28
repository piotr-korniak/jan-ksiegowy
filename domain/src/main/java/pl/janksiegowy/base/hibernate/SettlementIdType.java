package pl.janksiegowy.base.hibernate;

import pl.janksiegowy.settlement.SettlementId;

public class SettlementIdType extends UUIDDomainObjectIdCustomType<SettlementId>{
    public SettlementIdType() {
        super( SettlementIdTypeDescriptor.INSTANCE);
    }
}
