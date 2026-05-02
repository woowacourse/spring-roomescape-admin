package roomescape.time.repository;

import roomescape.time.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(Long id);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    boolean existsById(Long id);
}
