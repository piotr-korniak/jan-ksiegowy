@TypeDef( defaultForType= MetricId.class, typeClass= MetricIdType.class)
@GenericGenerator( name= "metric-id-generator", strategy= "org.hibernate.id.IdentityGenerator")

package pl.janksiegowy.metric;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.GenericGenerator;
import pl.janksiegowy.base.hibernate.MetricIdType;


