package roomescape.repository;

import java.util.List;
import roomescape.domain.time.Time;

public interface TimeRepository {
    Time save(Time timeRequest);

    List<Time> findAll();

    void deleteById(Long id);
}
