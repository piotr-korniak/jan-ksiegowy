package pl.janksiegowy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.auth.AuthenticationRequestDto;
import pl.janksiegowy.auth.AuthenticationResponseDto;
import pl.janksiegowy.subdomain.SubdomainController;
import pl.janksiegowy.subdomain.TenantController;

import java.net.URI;

@RequestMapping("/users")
@SubdomainController( value= { "app"})
public class UserController {

    @Autowired
    UserTenantService tenantService;

    @PostMapping
    ResponseEntity<UserDetails> create( @RequestBody AuthenticationRequestDto toCreate) {

        UserDetails result= tenantService.saveUser(toCreate.getUsername(), toCreate.getPassword());
        //ProjectDto result = projectFacade.save(toCreate);
        // AuthenticationResponseDto result
        return ResponseEntity.created( URI.create("/")).body( result);
    }
}
