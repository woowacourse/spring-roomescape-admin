package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.request.ReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> addReservationTime(@RequestBody ReservationTimeRequest request) {
        return ResponseEntity.ok(reservationTimeService.addReservationTime(request));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.getReservationTimes());
    }
}
