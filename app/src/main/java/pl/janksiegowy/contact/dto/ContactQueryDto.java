package pl.janksiegowy.contact.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public interface ContactQueryDto {

    @JsonProperty( "contactId")
    UUID getEntityId();
    String getName();
}
