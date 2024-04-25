package roomescape.reservationtime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationTimeApiController {

    private final ReservationTimeService reservationTimeService;

    private ReservationTimeApiController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    ResponseEntity<List<ReservationTime>> getAll() {
        List<ReservationTime> reservationTimes = reservationTimeService.getReservationTimes();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @PostMapping("/times")
    ResponseEntity<ReservationTime> create(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = reservationTimeService.saveReservationTime(reservationTimeRequest);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}