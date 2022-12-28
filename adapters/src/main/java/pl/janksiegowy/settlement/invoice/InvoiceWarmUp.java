package pl.janksiegowy.settlement.invoice;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import pl.janksiegowy.contact.ContactQueryRepository;
import pl.janksiegowy.entity.EntityRepository;
import pl.janksiegowy.metric.MetricRepository;

@Component
@Order( 2)
public class InvoiceWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private InvoiceInitializer initializer;

    public InvoiceWarmUp( final ResourceLoader loader,
                          final EntityRepository entityRepository,
                          final ContactQueryRepository contactRepository,
                          final InvoiceRepository invoiceRepository,
                          final MetricRepository metricRepository) {
        this.initializer= new InvoiceInitializer(
                loader,
                new InvoiceFactory(),
                entityRepository,
                contactRepository,
                invoiceRepository,
                metricRepository);
    }

    @Override
    public void onApplicationEvent( ContextRefreshedEvent event) {
        // initializer.init();
    }
}
