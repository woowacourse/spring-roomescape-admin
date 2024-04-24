package roomescape.domain.reservation.repository;

import java.util.List;
import roomescape.domain.reservation.Reservation;

public interface ReservationRepository {

    List<Reservation> findAllReservations();

    Reservation createReservation(Reservation reservation);

    void deleteReservation(Long id);
}
