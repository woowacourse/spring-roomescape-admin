package roomescape.domain.time.repository;

import java.util.List;
import roomescape.domain.time.Time;

public interface TimeRepository {

    List<Time> findAllTimes();

    Time createTime(Time time);

    void deleteTime(Long id);

    Time findTimeById(Long timeId);
}
