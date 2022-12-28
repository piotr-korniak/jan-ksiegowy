package pl.janksiegowy.settlement.invoice;

import pl.janksiegowy.settlement.SettlementId;

import java.util.Optional;

interface InvoiceRepository {

    Invoice save( Invoice invoice);

    Optional<Invoice> findById( SettlementId id);

}
