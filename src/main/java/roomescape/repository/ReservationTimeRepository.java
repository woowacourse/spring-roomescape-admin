package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    List<ReservationTime> findReservationTimes();

    Optional<ReservationTime> findReservationTimeById(Long id);

    ReservationTime createReservationTime(ReservationTime reservationTime);

    void deleteReservationTimeById(Long id);
}
