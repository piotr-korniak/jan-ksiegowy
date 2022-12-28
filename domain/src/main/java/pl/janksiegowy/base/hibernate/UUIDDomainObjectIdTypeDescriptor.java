package pl.janksiegowy.base.hibernate;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
import org.hibernate.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import pl.janksiegowy.base.support.UUIDDomainObjectId;

import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Hibernate type descriptor for a {@link UUIDDomainObjectId} subtype. You typically don't need to subclass this, it is
 * enough to instantiate it on demand or as a reusable constant.
 *
 * @param <ID> the ID type.
 * @see UUIDDomainObjectIdCustomType
 */
public class UUIDDomainObjectIdTypeDescriptor<ID extends UUIDDomainObjectId> extends AbstractTypeDescriptor<ID> {

    private final Function<UUID, ID> factory;

    /**
     * Creates a new {@code DomainObjectIdTypeDescriptor}.
     *
     * @param type    the ID type.
     * @param factory a factory for creating new ID instances.
     */
    public UUIDDomainObjectIdTypeDescriptor(@NotNull Class<ID> type, @NotNull Function<UUID, ID> factory) {
        super(type);
        this.factory = requireNonNull(factory);
    }

    @Override
    public String toString(ID value) {
        return UUIDTypeDescriptor.ToStringTransformer.INSTANCE.transform(value.unwrap());
    }

    @Override
    public ID fromString(String string) {
        System.err.println( "Dupa: "+ string);
        return factory.apply(UUIDTypeDescriptor.ToStringTransformer.INSTANCE.parse(string));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> X unwrap(ID value, Class<X> type, WrapperOptions options) {

        System.err.println( "Unwrap UU Domain (value): "+ value);
        if (value == null) {
            return null;
        }
        if (getJavaType().isAssignableFrom(type)) {
            System.err.println( "Unwrap UU Domain (type): JavaType");
            return (X) value;
        }
        if (UUID.class.isAssignableFrom(type)) {
            System.err.println( "Unwrap UU Domain (type): UUID");
            return (X) UUIDTypeDescriptor.PassThroughTransformer.INSTANCE.transform(value.unwrap());
        }
        if (String.class.isAssignableFrom(type)) {
            System.err.println( "Unwrap UU Domain (type): String");
            return (X) UUIDTypeDescriptor.ToStringTransformer.INSTANCE.transform(value.unwrap());
        }
        if (byte[].class.isAssignableFrom(type)) {
            System.err.println( "Unwrap UU Domain (type): byte[]");
            X dupa= (X) UUIDTypeDescriptor.ToBytesTransformer.INSTANCE.transform(value.unwrap());
            System.err.println( "Unwrap UU Domain (dupa): "+ dupa.toString());
            System.err.println( "Unwrap UU Domain (dupa): "+ dupa.getClass());
            return dupa;
        }
        throw unknownUnwrap(type);
    }


    @Override
    public <X> ID wrap(X value, WrapperOptions options) {
        System.err.println( "*** Wrap UU Domain: "+ value);
        if (value == null) {
            return null;
        }
        if (getJavaType().isInstance(value)) {
            return getJavaType().cast(value);
        }
        if (value instanceof UUID) {
            return factory.apply(UUIDTypeDescriptor.PassThroughTransformer.INSTANCE.parse(value));
        }
        if (value instanceof String) {
            return factory.apply(UUIDTypeDescriptor.ToStringTransformer.INSTANCE.parse(value));
        }
        if (value instanceof byte[]) {
            return factory.apply(UUIDTypeDescriptor.ToBytesTransformer.INSTANCE.parse(value));
        }
        throw unknownWrap(value.getClass());
    }

}

