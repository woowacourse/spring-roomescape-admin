package roomescape.time.repository;

import java.util.List;
import java.util.Optional;
import roomescape.time.domain.Time;

public interface TimeRepository {

    List<Time> findAll();

    Optional<Time> findById(Long id);

    Time save(Time time);

    void deleteById(Long id);
}
