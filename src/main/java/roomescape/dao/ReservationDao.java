package roomescape.dao;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationDao {

    List<Reservation> selectAll();

    Reservation insert(Reservation reservation);

    void delete(Long id);

}
