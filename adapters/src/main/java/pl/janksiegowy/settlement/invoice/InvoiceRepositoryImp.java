package pl.janksiegowy.settlement.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.janksiegowy.settlement.SettlementId;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class InvoiceRepositoryImp implements InvoiceRepository {
    private final SqlInvoiceRepository repository;

    private final JpaRepository<Invoice, SettlementId> dupa;

    public InvoiceRepositoryImp( final SqlInvoiceRepository repository, final  JpaRepository<Invoice, SettlementId> dupa) {
        this.repository = repository;
        this.dupa= dupa;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return repository.save( invoice);
    }

    @Override
    public Optional<Invoice> findById(SettlementId id) {
        return repository.findById( id);
    }
}


