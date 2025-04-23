package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(final ReservationTime reservationTime);
    List<ReservationTime> findAll();
    int deleteById(final long id);
}
