package roomescape.domain.time.repository;

import java.util.List;
import roomescape.domain.time.Time;

public interface TimeRepository {

    Time findById(Long timeId);

    List<Time> findAll();

    Time create(Time time);

    void delete(Long id);
}
