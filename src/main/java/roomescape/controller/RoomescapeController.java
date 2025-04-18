package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Reservation;
import roomescape.ReservationManager;
import roomescape.ReservationRequest;
import roomescape.ReservationResponse;
import roomescape.Reservations;

@RestController
public class RoomescapeController {

    private final Reservations reservations;
    private final ReservationManager reservationManager;

    public RoomescapeController(final Reservations reservations, final ReservationManager reservationManager) {
        this.reservations = reservations;
        this.reservationManager = reservationManager;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final List<ReservationResponse> reservationsDto = reservations.getReservations()
                .stream()
                .map(r -> new ReservationResponse(r.getId(), r.getName(), r.getDate(), r.getTime()))
                .toList();
        return ResponseEntity.ok(reservationsDto);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        final Reservation reservation = reservationManager.createReservation(request);
        reservations.add(reservation);
        return ResponseEntity.ok(new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime()));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.removeById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalException(IllegalArgumentException e) {
        System.out.println("IllegalArgumentException occurred: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Void> handleIllegalException(IllegalStateException e) {
        System.out.println("IllegalStateException occurred: " + e.getMessage());
        return ResponseEntity.internalServerError().build();
    }
}
