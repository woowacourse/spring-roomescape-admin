package roomescape.presentation.web.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.ReservationTimeService;
import roomescape.application.request.CreateReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.presentation.web.response.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> add(@RequestBody CreateReservationTimeRequest request) {
        ReservationTime newReservationTime = reservationTimeService.addTime(request);
        ReservationTimeResponse response = new ReservationTimeResponse(newReservationTime);

        return ResponseEntity.created(URI.create("/times/" + newReservationTime.getId()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        reservationTimeService.deleteBy(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        List<ReservationTime> times = reservationTimeService.findTimes();
        List<ReservationTimeResponse> responses = times.stream()
                .map(ReservationTimeResponse::new)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
