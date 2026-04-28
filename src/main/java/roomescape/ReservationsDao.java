package roomescape;

import java.util.List;

public interface ReservationsDao {
    List<Reservation> getReservations();
    Long addReservation(Reservation reservation);
    void deleteReservationById(Long id);
}
