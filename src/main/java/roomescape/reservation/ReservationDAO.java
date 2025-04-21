package roomescape.reservation;

import java.util.List;

public interface ReservationDAO {
    Reservation saveReservation(Reservation reservation);
    List<Reservation> findAllReservation();
    void deleteReservationById(long id);
}
