package roomescape.repository;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime insert(final LocalTime startAt);
}
