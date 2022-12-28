package pl.janksiegowy.base.hibernate;

import org.hibernate.id.IdentifierGenerator;
import pl.janksiegowy.metric.Metric;
import pl.janksiegowy.metric.MetricId;

public class MetricIdGenerator extends NumericDomainObjectIdGenerator<MetricId>{


    /**
     * Subclasses should declare a default constructor that delegates to this constructor.
     *
     * @param delegate       the underlying ID generator to delegate the ID generation to (typically one of Hibernate's built in numeric ID generators).
     * @param typeDescriptor the type descriptor for the ID.
     */
    protected MetricIdGenerator(IdentifierGenerator delegate, NumericDomainObjectIdTypeDescriptor<MetricId> typeDescriptor) {
        super(delegate, typeDescriptor);
    }
}
