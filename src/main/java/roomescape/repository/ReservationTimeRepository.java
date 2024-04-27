package roomescape.repository;

import roomescape.domain.reservation.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {
    ReservationTime add(ReservationTime time);

    Optional<ReservationTime> findById(Long id);


    List<ReservationTime> findAll();

    void delete(Long id);
}
