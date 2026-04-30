package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {
    ReservationTime createReservationTime(ReservationTime reservationTime);
    List<ReservationTime> findAll();
}
