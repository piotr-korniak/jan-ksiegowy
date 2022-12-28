package pl.janksiegowy.base.support;

import pl.janksiegowy.base.ValueObject;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Base class for simple {@linkplain ValueObject value objects} that wrap a value of some other type.
 *
 * @param <V> the type of the wrapped value.
 * @see SimpleValueObjectAttributeConverter
 */
public abstract class SimpleValueObject<V> implements ValueObject {

    private final V value;

    /**
     * Creates a new simple value object.
     *
     * @param value the value to wrap (never null).
     */
    public SimpleValueObject(@NotNull V value) {
        System.err.println( "SimpleValueObject: "+ value);
        this.value = requireNonNull(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Unwraps the value object.
     *
     * @return the wrapped value.
     */
    public @NotNull V unwrap() {
        System.err.println( "Unwrap Simple: "+ value);
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (SimpleValueObject<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

