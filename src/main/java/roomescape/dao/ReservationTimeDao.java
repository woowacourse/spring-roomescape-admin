package roomescape.dao;

import roomescape.entity.ReservationTime;

import java.util.List;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    ReservationTime insert(final ReservationTime reservationTime);

    boolean deleteById(final Long id);
}
