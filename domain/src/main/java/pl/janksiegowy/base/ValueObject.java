package pl.janksiegowy.base;

/**
 * Marker interface for value objects. Implementations of this interface should preferably be immutable and must
 * override {@link Object#equals(Object)} and {@link Object#hashCode()}.
 */
public interface ValueObject extends DomainObject {
}
