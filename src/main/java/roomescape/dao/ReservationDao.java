package roomescape.dao;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();

    Reservation create(String name, String date, ReservationTime time);

    void delete(Long id);
}
