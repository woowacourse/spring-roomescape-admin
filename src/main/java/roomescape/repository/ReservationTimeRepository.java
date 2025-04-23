package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime insert(final LocalTime startAt);

    List<ReservationTime> findAll();
}
