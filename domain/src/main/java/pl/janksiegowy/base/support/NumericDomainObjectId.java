package pl.janksiegowy.base.support;

import pl.janksiegowy.base.DomainObjectId;

/**
 * Base class for {@linkplain DomainObjectId domain object IDs} that wrap long integers.
 */
public abstract class NumericDomainObjectId extends SimpleValueObject<Long> implements DomainObjectId {

    /**
     * Creates a new {@code LongDomainObjectId} from the given numeric ID.
     *
     * @param id the numeric ID.
     */
    public NumericDomainObjectId(long id) {
        super( id);
    }
}
