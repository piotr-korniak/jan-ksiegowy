package pl.janksiegowy.settlement;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.base.AggregateRoot;
import pl.janksiegowy.metric.Metric;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name= "SETTLEMENTS")
@Inheritance( strategy= InheritanceType.JOINED)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
@Setter
@Accessors( chain= true)
public class Settlement extends AggregateRoot<SettlementId> {

    @Id
    @GeneratedValue( generator= "settlement-id-generator")
    private SettlementId id;

    @ManyToOne( fetch= FetchType.LAZY)
    private Metric metric;

    private Date date;

    private Date payment= new Date();

    @ManyToOne( fetch= FetchType.LAZY)
    private pl.janksiegowy.entity.Entity entity;

    public Settlement setDueDate( Date dueDate){
        this.payment= dueDate;
        return this;
    }
}
