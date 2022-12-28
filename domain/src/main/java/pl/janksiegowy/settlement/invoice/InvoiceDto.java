package pl.janksiegowy.settlement.invoice;

import lombok.Getter;
import pl.janksiegowy.contact.ContactDto;
import pl.janksiegowy.settlement.SettlementId;

import java.util.UUID;

@Getter
public class InvoiceDto {

    private SettlementId id;
    private UUID invoiceId;
    private UUID contactId;
}
