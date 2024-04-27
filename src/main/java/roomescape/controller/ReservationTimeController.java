package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.CreateReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> findAllReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.findAllTimes();
        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<CreateReservationTimeResponse> createTime(@RequestBody CreateReservationTimeRequest request) {
        CreateReservationTimeResponse response = reservationTimeService.createTime(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTime(@PathVariable long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
