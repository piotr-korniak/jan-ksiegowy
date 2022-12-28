package pl.janksiegowy.user;


import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table( name= "USERS")
public class UserJpa {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    public UserJpa setUsername( String username) {
        this.username= username;
        return this;
    }

    public UserJpa setPassword( String password) {
        this.password= password;
        return this;
    }

    public User create() {
        return new User( username, password, Set.of());
    }
}
