package roomescape.reservation.infra;

import java.time.LocalTime;
import java.util.List;
import roomescape.reservation.domain.Time;

public interface TimeRepository {
    Time save(LocalTime startAt);

    List<Time> findAll();
}
