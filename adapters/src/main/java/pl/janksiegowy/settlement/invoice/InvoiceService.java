package pl.janksiegowy.settlement.invoice;

import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private InvoiceRepository repository;

    public InvoiceService( InvoiceRepository repository) {
        this.repository = repository;
    }

    public Invoice save(Invoice invoice) {
        return repository.save( invoice);
    }
}
