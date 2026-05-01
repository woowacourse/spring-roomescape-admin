package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private ReservationTimeService service;

    public ReservationTimeController(ReservationTimeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationTimeResponse> getReservationTimes() {
        return service.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @PostMapping
    public ReservationTimeResponse createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTime reservationTime = service.create(request.getStartAt());
        return ReservationTimeResponse.from(reservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
