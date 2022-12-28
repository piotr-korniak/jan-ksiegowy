package pl.janksiegowy.base.hibernate;

import org.hibernate.id.ResultSetIdentifierConsumer;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.PostgresUUIDType;
import pl.janksiegowy.base.support.UUIDDomainObjectId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hibernate custom type for a {@link UUIDDomainObjectId} subtype. You need this to be able to use
 * {@link UUIDDomainObjectId}s as primary keys. You have to create one subclass per {@link UUIDDomainObjectId} subtype.
 *
 * @param <ID> the ID type.
 * @see UUIDDomainObjectIdTypeDescriptor
 * @see UUIDDomainObjectIdGenerator
 */
public abstract class UUIDDomainObjectIdCustomType<ID extends UUIDDomainObjectId> extends AbstractSingleColumnStandardBasicType<ID>
        implements ResultSetIdentifierConsumer {

    /**
     * Creates a new {@code DomainObjectIdCustomType}. In your subclass, you should create a default constructor and
     * invoke this constructor from there.
     *
     * @param domainObjectIdTypeDescriptor the {@link UUIDDomainObjectIdTypeDescriptor} for the ID type.
     */
    protected UUIDDomainObjectIdCustomType(@NotNull JavaTypeDescriptor<ID> domainObjectIdTypeDescriptor) {
        super( PostgresUUIDType.INSTANCE.getSqlTypeDescriptor(), domainObjectIdTypeDescriptor);
    }

    @Override
    public Serializable consumeIdentifier(ResultSet resultSet) {
        try {
            var id = resultSet.getBytes(1);
            System.err.println( "Może to tu!!! "+ id);
            return getJavaTypeDescriptor().wrap(id, null);
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not extract ID from ResultSet", ex);
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
