package pl.janksiegowy.base;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * Interface for domain objects that are identifiable by some unique ID (which may, but is not required to be,
 * a subtype of {@link DomainObjectId}).
 *
 * @param <ID> the ID type.
 * @see DomainObjectId
 */
public interface IdentifiableDomainObject<ID extends Serializable> extends DomainObject {

    /**
     * Returns the ID of the domain object.
     *
     * @return the ID (never null).
     * @throws IllegalStateException if the domain object does not have an ID.
     */
    @NotNull ID getIdentifier();

    /**
     * Returns whether the domain object has an ID or not. Typically a domain object only lacks an ID when it has
     * first been created but not yet saved.
     *
     * @return true if the domain object has an ID, false if it does not.
     */
    boolean hasIdentifier();
}

