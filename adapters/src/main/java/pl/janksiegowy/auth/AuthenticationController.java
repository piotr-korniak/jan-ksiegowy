package pl.janksiegowy.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.janksiegowy.subdomain.SubdomainController;

@RequestMapping( "/authenticate")
@SubdomainController( value= { "app"})
@AllArgsConstructor
class AuthenticationController {

    private AuthenticationManager authenticationManager;

    @PostMapping
    ResponseEntity<AuthenticationResponseDto> createToken( @RequestBody AuthenticationRequestDto request) {
        System.err.println( "createToken - post mapping: "+ request.getUsername());

        Authentication auth= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return ResponseEntity.ok( new AuthenticationResponseDto( "dupa"));
    }

}
