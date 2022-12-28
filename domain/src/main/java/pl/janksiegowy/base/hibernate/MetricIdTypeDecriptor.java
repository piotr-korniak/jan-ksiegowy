package pl.janksiegowy.base.hibernate;

import pl.janksiegowy.metric.MetricId;

public class MetricIdTypeDecriptor extends NumericDomainObjectIdTypeDescriptor<MetricId>{

    public MetricIdTypeDecriptor() {
        super( MetricId.class, MetricId::new);
    }

    public static final MetricIdTypeDecriptor INSTANCE= new MetricIdTypeDecriptor();
}
