package pl.janksiegowy.base.hibernate;

import org.hibernate.id.ResultSetIdentifierConsumer;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BigIntTypeDescriptor;
import pl.janksiegowy.base.support.NumericDomainObjectId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hibernate custom type for a {@link NumericDomainObjectId} subtype. You need this to be able to use
 * {@link NumericDomainObjectId}s as primary keys. You have to create one subclass per {@link NumericDomainObjectId}
 * subtype.
 *
 * @param <ID> the ID type.
 * @see NumericDomainObjectIdTypeDescriptor
 * @see NumericDomainObjectIdGenerator
 */
public abstract class NumericDomainObjectIdCustomType<ID extends NumericDomainObjectId> extends AbstractSingleColumnStandardBasicType<ID>
        implements ResultSetIdentifierConsumer {

    protected NumericDomainObjectIdCustomType(@NotNull JavaTypeDescriptor<ID> domainObjectIdTypeDescriptor) {
        super(BigIntTypeDescriptor.INSTANCE, domainObjectIdTypeDescriptor);
    }

    @Override
    public Serializable consumeIdentifier(ResultSet resultSet) {
        try {
            var id = resultSet.getLong(1);
            return getJavaTypeDescriptor().wrap(id, null);
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not extract ID from ResultSet");
        }
    }

    @Override
    public String getName() {
        return getJavaTypeDescriptor().getJavaType().getSimpleName();
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }
}
