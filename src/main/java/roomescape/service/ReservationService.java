package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.repository.Reservations;

@Service
public class ReservationService {

    private final Reservations reservations;

    public ReservationService(Reservations reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> findReservations() {
        return reservations.findReservations();
    }

    public Optional<Reservation> findReservationById(Long id) {
        return reservations.findReservationById(id);
    }

    public Reservation createReservation(ReservationCreateRequest reservationCreateRequest) {
        return reservations.createReservation(reservationCreateRequest);
    }

    public void deleteReservationById(Long id) {
        reservations.deleteReservationById(id);
    }
}
