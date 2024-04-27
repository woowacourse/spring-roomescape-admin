package roomescape.controller;

import java.util.List;
import java.util.Optional;
import roomescape.domain.ReservationTime;

public interface ReservationTimeRepository {
    List<ReservationTime> findReservationTimes();

    Optional<ReservationTime> findReservationTimeById(long id);

    ReservationTime createReservationTime(ReservationTime reservationTime);

    void deleteReservationTimeById(long id);
}
