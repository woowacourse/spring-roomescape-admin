package roomescape.repository;

import roomescape.entity.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    Long add(final ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void deleteById(final Long id);

    ReservationTime findById(final Long id);
}
