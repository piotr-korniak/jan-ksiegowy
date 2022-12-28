package pl.janksiegowy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.janksiegowy.settlement.invoice.InvoiceService;
import pl.janksiegowy.metric.Metric;
import pl.janksiegowy.metric.MetricRepositoryImp;
import pl.janksiegowy.tenant.ShardManagmenService;
import pl.janksiegowy.tenant.TenantContext;
import pl.janksiegowy.tenant.TenantManagmentServiceImpl;

import java.util.Optional;

@SpringBootTest
class JanKsiegowyApplicationTests {

    @Autowired
    ShardManagmenService shardManagmen;

    @Autowired
    TenantManagmentServiceImpl tenantManagment;

    //@Autowired
    //CompanyService company;

    //@Autowired
    //ContactQueryRepository contact;

    //EntityRepository entity;

    @Autowired
    MetricRepositoryImp metric;

    @Autowired
    InvoiceService invoice;


    @Test
    void contextLoads() {


        //tenantManagmen.find( "eleutheria");

        //shardManagmen.createShard( "jan-ksiegowy", "eleutheria", "Sylwia70");
        //company.findById( 1L);



        /*
        TenantContext.setTenantId( "eleutheria3", "pl5862321911");
        contact.findAll();
        company.findById( 1L);
        */


        // tworzenie Tenant-a
        TenantContext.setShardId("eleutheria");
        tenantManagment.createCompany("pl5862321911");

        /*
        for( Contact contact: contact.findAll()) {
            System.err.println( "Parent: "+ contact.getParent());
        }*/

        TenantContext.setTenantId("eleutheria", "pl5862321911");
        //contact.save( new ContactJpa());
       // Optional<Metric> dupa = metric.findById();
       // System.err.println( "Dupa: "+ dupa.get().getDate());

        /*
            Dodajemy root Contact
         */
/*
        Metric me= new Metric();
        me.setDate( new Date());
        me.setKapital( new BigDecimal( "0"));

        //me= metric.save( me);
        me.setParent( me);
        metric.save( me);
*/

/*
        Contact root= new Contact();

//        root= contact.save( root);
        root.setParent( root);
        contact.save( root);

        Invoice faktura= new Invoice();
        faktura.setContact( root);

        metric.findById()
                .ifPresent( faktura::setMetric);

        invoice.save( faktura);
*/
    }
}
