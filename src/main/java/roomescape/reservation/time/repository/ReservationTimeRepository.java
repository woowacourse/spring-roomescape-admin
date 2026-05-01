package roomescape.reservation.time.repository;

import roomescape.reservation.time.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {
    Optional<ReservationTime> findById(long timeId);

    List<ReservationTime> findAll();

    ReservationTime save(ReservationTime reservationTime);

    int deleteById(long id);
}
