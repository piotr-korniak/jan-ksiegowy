package pl.janksiegowy.base.hibernate;

import pl.janksiegowy.metric.MetricId;

public class MetricIdType extends NumericDomainObjectIdCustomType<MetricId> {
    public MetricIdType() {
        super( MetricIdTypeDecriptor.INSTANCE);
    }
}
