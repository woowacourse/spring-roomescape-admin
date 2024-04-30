package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(Long reservationTimeId);

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(Long reservationTimeId);
}
