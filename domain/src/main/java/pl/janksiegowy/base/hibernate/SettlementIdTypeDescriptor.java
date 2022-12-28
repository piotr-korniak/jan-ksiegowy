package pl.janksiegowy.base.hibernate;

import pl.janksiegowy.settlement.SettlementId;

public class SettlementIdTypeDescriptor extends UUIDDomainObjectIdTypeDescriptor<SettlementId>{

    public SettlementIdTypeDescriptor() {
        super( SettlementId.class, SettlementId::new );
    }

    public static final SettlementIdTypeDescriptor INSTANCE= new SettlementIdTypeDescriptor();
}
