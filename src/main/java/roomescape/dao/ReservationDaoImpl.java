package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDaoImpl {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();

    void deleteById(long reservationId);
}
