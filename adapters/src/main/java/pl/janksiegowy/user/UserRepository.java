package pl.janksiegowy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserJpa, Long> {

    UserJpa save( UserJpa entity);
    Optional<UserJpa> findByUsername( String username);

}
