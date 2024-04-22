package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.getReservations();
        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> addReservationTime(@RequestBody ReservationTimeRequest request) {
        long id = reservationTimeService.addReservationTime(new ReservationTime(request.startAt()));
        ReservationTime reservationTime = reservationTimeService.getReservationTime(id);
        return ResponseEntity.created(URI.create("/times/" + id)).body(reservationTime);
    }
}
