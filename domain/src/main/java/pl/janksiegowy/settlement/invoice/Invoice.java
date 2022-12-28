package pl.janksiegowy.settlement.invoice;

import lombok.Data;
import lombok.Setter;
import pl.janksiegowy.base.AggregateRoot;
import pl.janksiegowy.contact.Contact;
import pl.janksiegowy.contact.ContactDto;
import pl.janksiegowy.metric.Metric;
import pl.janksiegowy.settlement.Settlement;
import pl.janksiegowy.settlement.SettlementId;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table( name= "INVOICES")
@DiscriminatorValue( value= "I")
@PrimaryKeyJoinColumn( name = "settlementId")
public class Invoice extends Settlement {
    @Column( updatable = false, insertable = false)
    private UUID settlementId;

    @Column( name= "date")
    private Date invoiceDate= new Date();

    public Invoice setContact( Contact contact){
        System.err.println( "Contact: "+ contact.getTaxNumber());

        setEntity( contact);
        return this;
    }

    public Invoice setInvoiceDate( Date invoiceDate){
        this.invoiceDate= invoiceDate;
        return this;
    }

}
