package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    long save(ReservationTime reservationTime);

    List<ReservationTime> findAll();
}
