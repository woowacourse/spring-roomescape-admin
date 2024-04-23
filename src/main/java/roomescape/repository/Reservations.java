package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;

public interface Reservations {
    List<Reservation> findReservations();

    Optional<Reservation> findReservationById(Long id);

    Reservation createReservation(ReservationCreateRequest reservationCreateRequest);

    void deleteReservationById(Long id);
}
