package pl.janksiegowy.contact;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.contact.dto.ContactQueryDto;
import pl.janksiegowy.subdomain.TenantController;
import pl.janksiegowy.tenant.TenantContext;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@TenantController
@RequestMapping( "/v2/contacts")
@AllArgsConstructor
public class ContactController {

    private  ContactQueryRepository query;

    @GetMapping
    ResponseEntity<List<ContactQueryDto>> list() {
        TenantContext.setTenantId( TenantContext.getShardId(), "pl5862321911");

        Date initDate= new Date( 300_600_000L);
       // initDate= new Date();
/*
        query.findByContactIdAndDate( UUID.fromString("d4de442a-ebc6-416b-bfdd-e22e4242767f"),
                            initDate);
*/
        return ResponseEntity.ok( query.findBy( ContactQueryDto.class));
    }

}
