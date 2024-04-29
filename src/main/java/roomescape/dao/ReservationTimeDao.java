package roomescape.dao;

import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationTimeDao {
    ReservationTime create(ReservationTime reservationTime);

    List<ReservationTime> findAll();

    void delete(long id);
}
