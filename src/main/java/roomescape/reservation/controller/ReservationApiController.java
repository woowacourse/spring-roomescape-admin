package roomescape.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.service.ReservationService;

import java.util.List;

@RestController
public class ReservationApiController {

    private final ReservationService reservationService;

    private ReservationApiController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAll() {
        List<ReservationResponse> reservationResponses = reservationService.getReservations();
        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Object> create(@RequestBody @Valid final ReservationRequest reservationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("적절하지 않는 입력값 입니다.");
        }
        final ReservationResponse newReservationResponse = reservationService.saveReservation(reservationRequest);
        return ResponseEntity.ok().body(newReservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        reservationService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}
