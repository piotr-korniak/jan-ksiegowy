package pl.janksiegowy.metric;

import lombok.Getter;
import lombok.Setter;
import pl.janksiegowy.base.AggregateRoot;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table( name= "METRICS" )
@Getter
@Setter
public class Metric extends AggregateRoot<MetricId> implements Serializable {

    @Id
    @GeneratedValue( generator= "metric-id-generator")
    private MetricId id;

    private Date date;

    private BigDecimal kapital;
/*
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn( name = "PARENT_ID", referencedColumnName = "PARENT_ID",
            updatable= false, insertable= false)
    private Set<Metric> self;
*/
    /*
    @ManyToOne( fetch= FetchType.LAZY)
    private Metric parent;*/
}
