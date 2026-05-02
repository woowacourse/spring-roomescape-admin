package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {
    ReservationTime addTime(ReservationTime reservationTime);

    List<ReservationTime> findAllReservationTimes();

    void deleteTime(Long id);

    ReservationTime findById(Long id);
}
