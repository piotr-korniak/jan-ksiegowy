package pl.janksiegowy.base.hibernate;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import pl.janksiegowy.base.support.NumericDomainObjectId;

import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Hibernate type descriptor for a {@link NumericDomainObjectId} subtype. You typically don't need to subclass this, it
 * is enough to instantiate it on demand or as a reusable constant.
 *
 * @param <ID> the ID type.
 * @see NumericDomainObjectIdCustomType
 */
   public class NumericDomainObjectIdTypeDescriptor<ID extends NumericDomainObjectId> extends AbstractTypeDescriptor<ID> {

        private final Function<Long, ID> factory;

    /**
     * Creates a new {@code NumericDomainObjectIdTypeDescriptor}.
     *
     * @param type    the ID type.
     * @param factory a factory for creating new ID instances.
     */
    public NumericDomainObjectIdTypeDescriptor(@NotNull Class<ID> type, @NotNull Function<Long, ID> factory) {
        super(type);
        this.factory = requireNonNull(factory);
    }

    @Override
    public String toString(ID value) {
        return value.toString();
    }

    @Override
    public ID fromString(String s) {
        return factory.apply(Long.parseLong(s));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> X unwrap(ID value, Class<X> type, WrapperOptions options) {

        System.err.println( "Unwrap UU Domain (value): "+ value);
        if (value == null) {
            return null;
        }
        if (getJavaType().isAssignableFrom(type)) {
            return (X) value;
        }
        if (Long.class.isAssignableFrom(type)) {
            System.err.println( "Unwrap UU Domain (type): Long");
            return (X) value.unwrap();
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) value.toString();
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> ID wrap(X value, WrapperOptions wrapperOptions) {
        if (value == null) {
            return null;
        }
        if (getJavaType().isInstance(value)) {
            return getJavaType().cast(value);
        }
        if (value instanceof Long) {
            return factory.apply((Long) value);
        }
        if (value instanceof String) {
            return factory.apply(Long.parseLong((String) value));
        }
        throw unknownWrap(value.getClass());
    }
}

