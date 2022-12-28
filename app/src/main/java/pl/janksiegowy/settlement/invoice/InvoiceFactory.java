package pl.janksiegowy.settlement.invoice;

import lombok.AllArgsConstructor;
import pl.janksiegowy.entity.EntityRepository;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class InvoiceFactory {

    public Invoice from() {//} InvoiceDto source) {





        return new Invoice();
        //        .setEntity();
    }
}
