package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDTO;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDTO requestDTO) {
        Reservation newReservation = reservationService.createReservation(requestDTO);
        return ResponseEntity.ok().body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservationService.findAllReservations());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
