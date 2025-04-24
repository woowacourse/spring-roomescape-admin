package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(Long id);

    ReservationTime findById(Long id);
}
