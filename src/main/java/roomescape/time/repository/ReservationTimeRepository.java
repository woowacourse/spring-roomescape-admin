package roomescape.time.repository;

import roomescape.time.entity.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    void deleteById(Long id);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();
}
