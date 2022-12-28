package pl.janksiegowy.metric;

import pl.janksiegowy.base.support.NumericDomainObjectId;

public class MetricId extends NumericDomainObjectId {

    public MetricId( long id) {
        super( id);
        System.err.println( "Tworzymy Id: "+ id );
    }
}
