package pl.janksiegowy.metric;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Repository
public class MetricRepositoryImp implements MetricRepository {

    private SqlMetricRepository repository;

    public MetricRepositoryImp( SqlMetricRepository repository) {
        this.repository= repository;
    }

    @Override
    public Optional<Metric> findById( MetricId id) {
        return repository.findById( id);
    }

    @Override
    public Optional<Metric> findIdByDate( Date date) {
        return repository.findIdByDate( date);
    }

    public Metric save(Metric metric) {
        return repository.save( metric);
    }

}
