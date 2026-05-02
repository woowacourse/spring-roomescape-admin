package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.TimeRequest;
import roomescape.controller.dto.TimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@RequestBody TimeRequest request) {
        ReservationTime savedReservationTime = reservationTimeService.create(request.startAt());

        TimeResponse timeResponse = TimeResponse.from(savedReservationTime);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(timeResponse);
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> findAll() {

        List<TimeResponse> timeResponses = reservationTimeService.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();

        return ResponseEntity.ok(timeResponses);
    }


    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
