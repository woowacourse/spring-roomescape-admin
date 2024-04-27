package roomescape.controller;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> findReservations();

    Optional<Reservation> findReservationById(long id);

    Reservation createReservation(Reservation reservation);

    void deleteReservationById(long id);
}
