package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.controller.dto.ReservationTimeCreateResponse;
import roomescape.entity.ReservationTime;
import roomescape.service.TimeService;

@RequestMapping("/times")
@RestController
public class AvailableTimeController {
    private final TimeService timeService;

    public AvailableTimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationTimeCreateResponse>> readAllTimes() {
        List<ReservationTimeCreateResponse> availableTimes = timeService.readAll().stream()
                .map(ReservationTimeCreateResponse::from)
                .toList();
        return ResponseEntity.ok().body(availableTimes);
    }

    @PostMapping()
    public ResponseEntity<ReservationTimeCreateResponse> createAvailableTime(
            @RequestBody ReservationTimeCreateRequest request) {
        ReservationTime saved = timeService.save(request.toEntity());
        return ResponseEntity.ok().body(ReservationTimeCreateResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailableTime(@PathVariable("id") long id) {
        timeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
