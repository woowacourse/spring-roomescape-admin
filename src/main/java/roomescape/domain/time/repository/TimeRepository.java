package roomescape.domain.time.repository;

import java.util.List;
import roomescape.domain.time.domain.Time;

public interface TimeRepository {

    Time save(Time time);

    List<Time> findAllTimes();

    void deleteTimeById(Long id);
}
