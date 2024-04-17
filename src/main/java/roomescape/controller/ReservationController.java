package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.scheduler.ReservationScheduler;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationScheduler reservationScheduler;

    public ReservationController(ReservationScheduler reservationScheduler) {
        this.reservationScheduler = reservationScheduler;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAll() {
        List<ReservationResponse> totalReservations = reservationScheduler.getAllReservations();
        return ResponseEntity.ok(totalReservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationDto) {
        ReservationResponse response = reservationScheduler.scheduleReservation(reservationDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationScheduler.cancelReservation(id);
        return ResponseEntity.ok().build();
    }
}
