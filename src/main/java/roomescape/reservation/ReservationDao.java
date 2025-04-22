package roomescape.reservation;

import java.util.List;

public interface ReservationDao {
    Reservation saveReservation(Reservation reservation);
    List<Reservation> findAllReservation();
    void deleteReservationById(long id);
}
