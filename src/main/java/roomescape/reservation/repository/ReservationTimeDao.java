package roomescape.reservation.repository;

import roomescape.reservation.entity.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeDao {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(Long id);

    Optional<ReservationTime> findById(Long id);
}
