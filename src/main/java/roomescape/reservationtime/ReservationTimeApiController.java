package roomescape.reservationtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationTimeApiController {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @PostMapping("/times")
    ResponseEntity<ReservationTime> create(@RequestBody final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = reservationTimeService.saveReservationTime(reservationTimeRequest);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.getReservationTimes();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}