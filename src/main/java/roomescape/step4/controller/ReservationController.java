package roomescape.step4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.step4.domain.Reservation;
import roomescape.step4.dto.ReservationRequest;
import roomescape.step4.dto.ReservationResponse;
import roomescape.step4.service.ReservationService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController("reservationControllerStep4")
@RequestMapping("/step4/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<Reservation> reservations = reservationService.findAll();
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.save(request);
        ReservationResponse response = ReservationResponse.from(reservation);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            reservationService.delete(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
