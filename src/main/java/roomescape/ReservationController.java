package roomescape;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = reservationService.findReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> add(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.addReservation(reservationRequest);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> addTime(@RequestBody ReservationTime reservationTime) {
        ReservationTime newTime = reservationService.addReservationTime(reservationTime);
        return ResponseEntity.ok().body(newTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readTimes() {
        List<ReservationTime> reservationTimes = reservationService.findReservationTimes();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
