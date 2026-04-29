package roomescape.dao;

import roomescape.Reservation;

import java.util.List;

public interface ReservationDao {

    Reservation select(Long id);

    List<Reservation> selectAll();

    Reservation insert(Reservation reservation);

    void delete(Long id);

}
