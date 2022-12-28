package pl.janksiegowy.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
class UserTenantService implements UserDetailsService {

    private PasswordEncoder encoder;
    private UserRepository userRepository;

    public UserTenantService( PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder= encoder;
        this.userRepository= userRepository;
/*
        userRepository.save( new UserJpa()
            .setUsername( "user")
            .setPassword( encoder.encode( "user")));
*/  }

    public UserDetails saveUser( String username, String password){
        return userRepository.save( new UserJpa()
                                        .setUsername( username)
                                        .setPassword( encoder.encode( password))
                ).create();
    }

    @Override
    public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException {
        return userRepository.findByUsername( username)
            .map( UserJpa::create)
            .orElseThrow( ()->
                new UsernameNotFoundException( String.format( "The username %s doesn't exist", username)));
    }

}
