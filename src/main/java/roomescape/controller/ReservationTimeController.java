package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@Controller
@RequestMapping("times")
public class ReservationTimeController {
    private final ReservationTimeService timeService;

    public ReservationTimeController(ReservationTimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse response = timeService.create(request);
        return ResponseEntity.created(URI.create("times/" + response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        return ResponseEntity.ok(timeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        timeService.deleteBy(id);
        return ResponseEntity.ok().build();
    }
}
