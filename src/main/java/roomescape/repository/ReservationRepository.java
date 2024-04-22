package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> findReservations();

    Optional<Reservation> findReservationById(Long id);

    Long createReservation(ReservationCreateRequest reservationCreateRequest);

    void deleteReservationById(Long id);

}
