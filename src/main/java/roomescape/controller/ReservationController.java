package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.makeReservation(reservationRequest);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readAllReservation() {
        List<Reservation> reservations = reservationService.findAllReservation();

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTime newReservationTime) {
        ReservationTime reservationTime = reservationService.makeReservationTime(newReservationTime);
        return ResponseEntity.ok(reservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readAllReservationTime() {
        List<ReservationTime> reservationTimes = reservationService.findAllReservationTime();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationService.deleteReservationTimeById(id);
        return ResponseEntity.ok().build();
    }
}
