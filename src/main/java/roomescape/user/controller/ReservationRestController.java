package roomescape.user.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.exception.DataNotFoundException;
import roomescape.user.controller.dto.ReservationRequest;
import roomescape.user.controller.dto.ReservationResponse;
import roomescape.user.domain.Reservation;
import roomescape.user.service.ReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationRestController {

    private final ReservationService reservationService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Void> handleDataNotFoundException(final DataNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> persistReservation(
            @RequestBody final ReservationRequest reservationRequest
    ) {
        final Long id = reservationService.save(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.timeId()
        );
        final Reservation found = reservationService.getById(id);
        return ResponseEntity.ok(ReservationResponse.from(found));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> retrieveReservations() {
        final List<Reservation> reservations = reservationService.findAll();
        final List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        reservationService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
