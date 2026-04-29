package roomescape.dao;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();

    Reservation create(String name, String date, String time);

    void delete(Long id);
}
