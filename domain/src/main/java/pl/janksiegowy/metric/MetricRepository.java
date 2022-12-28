package pl.janksiegowy.metric;

import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface MetricRepository {

    Optional<Metric> findById( MetricId id);
    Optional<Metric> findIdByDate( Date date);
    Metric save( Metric metric);
}
