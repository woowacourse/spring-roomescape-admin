package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.model.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation insertReservation(Reservation reservation);

    boolean deleteReservationById(long id);
}
