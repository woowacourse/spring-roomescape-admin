package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.reservation.request.ReservationRequestDto;
import roomescape.dto.reservation.response.ReservationResponseDto;
import roomescape.dto.reservation.response.ReservationsResponseDto;
import roomescape.service.reservation.ReservationService;

import java.net.URI;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<ReservationsResponseDto> getReservationDatum() {
        return ResponseEntity.ok(reservationService.findAllReservation());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> addReservationData(@RequestBody final ReservationRequestDto request) {
        ReservationResponseDto response = reservationService.addReservation(request);

        return ResponseEntity.created(URI.create("/reservations/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationsData(@PathVariable final Long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
