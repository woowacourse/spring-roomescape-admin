package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;
import roomescape.valid.ValidationResult;
import roomescape.valid.ValidationUtils;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        final List<ReservationResponse> dtos = reservationService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody final ReservationRequest reservationRequest) {
        ValidationResult validationResult = ValidationUtils.validate(reservationRequest);
        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest().body(validationResult.errorResponse());
        }

        final Reservation reservation = makeReservation(reservationRequest);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable final Long id) {
        reservationService.delete(id);
    }

    private Reservation makeReservation(final ReservationRequest reservationRequest) {
        try {
            final Reservation reservation = reservationRequest.fromEntity();
            return reservationService.insert(reservation);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
