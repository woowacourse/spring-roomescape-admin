package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDAO {

    List<Reservation> findAllReservation();

    Long insertReservation(Reservation reservation);

    int deleteReservationById(Long id);
}
