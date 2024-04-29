package roomescape.repository;

import java.util.List;
import roomescape.model.Reservation;

public interface ReservationRepository {
    List<Reservation> getAllReservations();

    Reservation addReservation(Reservation reservation);

    long deleteReservation(long id);
}
