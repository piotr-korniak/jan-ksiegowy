package pl.janksiegowy.metric;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

public interface SqlMetricRepository extends JpaRepository<Metric, MetricId> {

    @Query( name= "SELECT m FROM Metric m WHERE m.id= 1" )
    Optional<Metric> findById( MetricId id);

    @Query( value=
            "SELECT * FROM METRICS m " +
            "LEFT OUTER JOIN METRICS p " +
            "ON p.date <= :date AND m.date < p.date "+
        //  "ON m.parent_id= p.parent_id AND (p.date <= :date AND m.date < p.date) "+
            "WHERE m.date <= :date AND p.date IS NULL",
            nativeQuery = true )
    Optional<Metric> findIdByDate( @Param( "date") Date date);

    Metric save( Metric metric);

}
