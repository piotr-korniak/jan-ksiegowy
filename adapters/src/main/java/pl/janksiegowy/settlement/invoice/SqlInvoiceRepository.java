package pl.janksiegowy.settlement.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.janksiegowy.settlement.SettlementId;

import java.util.Optional;

interface SqlInvoiceRepository extends JpaRepository<Invoice, SettlementId> {
    public Invoice save(Invoice invoice);
    public Optional<Invoice> findById(SettlementId id);
};
