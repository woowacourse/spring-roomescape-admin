package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> findReservations();

    Optional<Reservation> findReservationById(Long id);

    Reservation createReservation(Reservation reservation);

    void deleteReservationById(Long id);
}
