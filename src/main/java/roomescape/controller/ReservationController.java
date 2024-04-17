package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.storage.ReservationStorage;

@RestController
public class ReservationController {

    private final ReservationStorage reservationStorage;

    public ReservationController(ReservationStorage reservationStorage) {
        this.reservationStorage = reservationStorage;
    }

    @PostMapping("/reservations")
    public ReservationResponse saveReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationStorage.save(reservationRequest);
        return toResponse(reservation);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> findAllReservations() {
        return reservationStorage.findAllReservations()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @DeleteMapping("/reservations/{reservationId}")
    public void delete(@PathVariable Long reservationId) {
        reservationStorage.delete(reservationId);
    }
}
