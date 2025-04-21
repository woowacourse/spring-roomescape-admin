package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import roomescape.Reservations;
import roomescape.controller.dto.ReservationRequest;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationResponse;

@RestController
@RequiredArgsConstructor
public class RoomescapeController {

    private final ReservationService reservationService;
    private final Reservations reservations;
    private final ReservationManager reservationManager;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final List<ReservationResponse> reservationResponses = reservationService.getReservations();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        final Reservation reservation = reservationManager.createReservation(request);
        reservations.add(reservation);
        return ResponseEntity.ok(
                new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                        reservation.getTime()));
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
