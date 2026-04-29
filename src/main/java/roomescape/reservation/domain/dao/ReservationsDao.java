package roomescape.reservation.domain.dao;

import java.util.List;
import roomescape.reservation.domain.Reservation;

public interface ReservationsDao {
    List<Reservation> getReservations();
    Long saveReservation(Reservation reservation);
    void deleteReservationById(Long id);
}
