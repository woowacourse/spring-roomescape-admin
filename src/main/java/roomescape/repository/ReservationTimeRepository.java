package roomescape.repository;

import roomescape.domain.entity.ReservationTime;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    Long save(ReservationTime reservationTime);
    List<ReservationTime> findAll();
    Optional<ReservationTime> findById(Long id);
    void delete(Long id);
}
