package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.request.ReservationRequestDto;
import roomescape.reservation.dto.response.ReservationResponseDto;
import roomescape.reservation.dto.response.ReservationsResponseDto;
import roomescape.reservation.service.ReservationService;

import java.net.URI;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationServiceImpl) {
        this.reservationService = reservationServiceImpl;
    }

    @GetMapping("/reservations")
    public ResponseEntity<ReservationsResponseDto> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAllReservation());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> saveReservation(@RequestBody final ReservationRequestDto request) {
        ReservationResponseDto response = reservationService.addReservation(request);

        return ResponseEntity.created(URI.create("/reservations/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable final Long id) {
        reservationService.removeReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
