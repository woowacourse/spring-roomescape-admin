package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {
    List<ReservationTime> findAll();

    long create(ReservationTime reservationTime);

    boolean deleteById(long id);

    ReservationTime findById(long id);
}
