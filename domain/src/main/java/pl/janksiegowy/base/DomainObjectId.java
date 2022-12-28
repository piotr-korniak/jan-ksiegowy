package pl.janksiegowy.base;

import java.io.Serializable;

/**
 * Marker interface for value objects that act as identifiers of {@link IdentifiableDomainObject}s.
 */
public interface DomainObjectId extends ValueObject, Serializable {
}
