package pl.janksiegowy.settlement.invoice;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.contact.ContactDto;
import pl.janksiegowy.contact.ContactQueryRepository;
import pl.janksiegowy.entity.EntityRepository;
import pl.janksiegowy.metric.MetricRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.text.SimpleDateFormat;

@AllArgsConstructor
public class InvoiceInitializer {

    private final ResourceLoader loader;
    private final InvoiceFactory factory;
    private final EntityRepository entityRepository;
    private final ContactQueryRepository contactRepository;
    private final InvoiceRepository invoiceRepository;
    private final MetricRepository metricRepository;

    @SneakyThrows
    public void init() {
        SimpleDateFormat DMRR= new SimpleDateFormat( "dd.MM.yyyy");

        for( String line: readIvoices()) {
            String[] fields= getFields( line);
            if( fields[0].startsWith( "---"))
                continue;

            Invoice invoice= factory.from();
            Date invoiceDate= DMRR.parse( fields[4]);

            metricRepository.findIdByDate( invoiceDate)
                    .ifPresent( metric-> { invoice.setMetric( metric);});

            search: {   // search Contact for the Invoice date
                for( ContactDto contact : contactRepository.findByTaxNumber( ContactDto.class,
                        fields[2].replaceAll("[^\\d]", ""))) {
//                    entityRepository.findContactByEntityIdAndDate( contact.getEntityId(), invoiceDate)
  //                          .ifPresent( entity-> { invoice.setContact( entity);});
                    break search;
                }}

            invoiceRepository.save( (Invoice)invoice
                    .setInvoiceDate( invoiceDate)
                    .setDate( DMRR.parse( fields[5]))
                    .setDueDate( DMRR.parse( fields[6])));

        }


    }

    @SneakyThrows
    private String[] readIvoices() {
        return new String( loader
                .getResource("classpath:data/invoices.txt")
                .getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .split( "; *["+ System.lineSeparator()+ "]*");
    }

    private String[] getFields( String row){
        String[] result= row.split( "(?<!\\\\), *");

        for( int n=0; n<result.length; n++)	// nie zmienia jak nie trzeba
            result[n]= result[n].replaceAll( "\\\\,", ",");
        return result;
    }
}
