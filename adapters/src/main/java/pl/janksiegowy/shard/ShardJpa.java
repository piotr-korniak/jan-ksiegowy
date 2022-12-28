package pl.janksiegowy.shard;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table( name= "SHARDS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShardJpa {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private Long id;

    String name;

    @Override
    public int hashCode()
    {
        return Objects.hash( name);
    }
}
