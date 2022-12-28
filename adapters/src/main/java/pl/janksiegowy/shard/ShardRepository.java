package pl.janksiegowy.shard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShardRepository extends JpaRepository<ShardJpa, Long> {

    public Optional<ShardJpa> findByName( String name);
}
