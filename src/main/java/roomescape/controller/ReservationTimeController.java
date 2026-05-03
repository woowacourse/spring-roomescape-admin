package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeRequest;
import roomescape.service.ReservationTimeService;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public List<ReservationTime> read() {
        return reservationTimeService.read();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeRequest reservationTimeReq) {
        ReservationTime newReservationTime = reservationTimeService.create(reservationTimeReq);
        URI uri = URI.create("/times/" + newReservationTime.getId());
        return ResponseEntity.created(uri).body(newReservationTime);
    }

    @PutMapping("/times/{id}")
    public ResponseEntity<Void> update(@RequestBody ReservationTimeRequest newReservationTimeReq, @PathVariable Long id) {
        reservationTimeService.update(newReservationTimeReq, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
