package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.util.List;

@Controller
public class ReservationTimeController {

    private final ReservationTimeService service;

    public ReservationTimeController(final ReservationTimeService service) {
        this.service = service;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> add(@RequestBody ReservationTimeCreateRequest request) {
        final ReservationTime reservationTime = service.saveAndGet(request);
        final ReservationTimeResponse response = ReservationTimeResponse.from(reservationTime);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getAll() {
        final List<ReservationTime> reservationTimes = service.getAll();
        final List<ReservationTimeResponse> response = ReservationTimeResponse.fromList(reservationTimes);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity<Void> delete(@PathVariable("reservationTimeId") long id) {
        service.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
