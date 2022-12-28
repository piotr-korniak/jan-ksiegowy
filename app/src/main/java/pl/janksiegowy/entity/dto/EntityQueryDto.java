package pl.janksiegowy.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

public interface EntityQueryDto {
    @JsonProperty( "contactId")
    UUID getEntityId();
    String getName();
    Date getDate();
}
