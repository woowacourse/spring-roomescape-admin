package roomescape.repository;

import java.util.List;
import roomescape.model.Reservation;

public interface ReservationDAO {
    List<Reservation> selectAllReservations();

    Reservation insertReservation(Reservation reservation);

    long deleteReservation(long id);
}
