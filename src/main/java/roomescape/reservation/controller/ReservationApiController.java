package roomescape.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.controller.request.ReservationCreateRequest;
import roomescape.reservation.controller.response.ReservationResponse;
import roomescape.reservation.controller.response.ReservationsResponse;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.Reservations;
import roomescape.reservation.domain.exception.ReserverNameEmptyException;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.service.exception.ReservationNotFoundException;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<ReservationsResponse> getReservations() {
        Reservations reservations = reservationService.findReservations();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReservationsResponse.from(reservations));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        Reservation reservation;
        try {
            reservation = reservationService.createReservation(request);
        } catch (ReserverNameEmptyException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                ReservationResponse.from(reservation)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservation;
        try {
            reservation = reservationService.findReservation(id);
        } catch (ReservationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        reservationService.delete(reservation);

        return ResponseEntity.ok().build();
    }
}
