package roomescape.reservationtime.dao;

import java.util.List;
import roomescape.reservationtime.ReservationTime;

public interface ReservationTimeDao {
    List<ReservationTime> findAll();

    Long create(ReservationTime reservationTime);

    int delete(Long id);

    ReservationTime findById(Long id);
}
