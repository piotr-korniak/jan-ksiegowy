package pl.janksiegowy.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.util.Set;

@EnableWebSecurity
class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain( HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
            .antMatchers("/authenticate").permitAll()
            .antMatchers( "/users").permitAll()
            .antMatchers( "/v2/contacts").permitAll()
            .antMatchers( "/error").permitAll() // for JSON error response
            .antMatchers( "/h2-console/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore( new AuthenticationFilter(), AnonymousAuthenticationFilter.class)
            .sessionManagement()
            .sessionCreationPolicy( SessionCreationPolicy.STATELESS)
            .and()
            .headers().frameOptions().disable();    // for H2 console
        return http.build();
    }

    // https://www.baeldung.com/registration-with-spring-mvc-and-spring-security
/*
    @Bean
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                new User(
                        "user",
                        passwordEncoder().encode("user"),
                        Set.of()
                ),
                new User(
                        "admin",
                        passwordEncoder().encode("admin"),
                        Set.of(new SimpleGrantedAuthority("admin"))
                )
        );
    }
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager( AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}