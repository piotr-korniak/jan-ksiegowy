package pl.janksiegowy.entity;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import pl.janksiegowy.tenant.TenantContext;

@Component
@Order( 1)
class EntityWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private final EntityInitializer initializer;

    public EntityWarmUp( final ResourceLoader loader,
                         final EntityQueryRepository entity,
                         final EntityFacade facade) {
        this.initializer= new EntityInitializer( loader, entity, facade);

        TenantContext.setTenantId( "eleutheria", "pl5862321911");
    }

    @Override
    public void onApplicationEvent( ContextRefreshedEvent event) {
        initializer.init();
    }
}
