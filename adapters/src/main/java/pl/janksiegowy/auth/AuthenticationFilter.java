package pl.janksiegowy.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.janksiegowy.tenant.TenantContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

class AuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    //private final UserDetailsService userDetailsService;

   /* public AuthenticationFilter( UserDetailsService userDetailsService) {
        this.userDetailsService= userDetailsService;
    }*/

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(
            request.getHeader( HttpHeaders.AUTHORIZATION)
        ).filter( authHeader-> authHeader.startsWith( BEARER))
                .map( authHeader-> authHeader.substring( BEARER.length()))
                .ifPresent( token-> {

                });

        // authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails( request));
        // SecurityContextHolder.getContext().setAuthentication( authentication);
        TenantContext.setTenantId( "eleutheria", "pl5862321911");

        filterChain.doFilter( request, response);
    }
}
