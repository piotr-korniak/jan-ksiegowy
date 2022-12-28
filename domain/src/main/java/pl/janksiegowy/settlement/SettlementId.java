package pl.janksiegowy.settlement;

import pl.janksiegowy.base.support.UUIDDomainObjectId;

import java.util.UUID;

public class SettlementId extends UUIDDomainObjectId {
    public SettlementId( UUID uuid) {
        super( uuid);
    }

    public SettlementId() {

    }
}
