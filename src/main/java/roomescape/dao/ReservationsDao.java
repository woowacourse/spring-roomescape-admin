package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationsDao {
    List<Reservation> getReservations();
    Long addReservation(Reservation reservation);
    void deleteReservationById(Long id);
}
