package roomescape.reservation.infra;

import java.time.LocalTime;
import roomescape.reservation.domain.Time;

public interface TimeRepository {
    Time save(LocalTime startAt);
}
