package roomescape.reservation.time.repository;

import roomescape.reservation.time.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {
    ReservationTime findById(Long timeId);

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(Long id);
}
