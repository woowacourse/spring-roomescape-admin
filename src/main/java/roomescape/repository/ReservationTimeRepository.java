package roomescape.repository;

import roomescape.entity.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime findById(Long id);

    ReservationTime save(ReservationTime reservationTime);

    int deleteById(Long id);
}
