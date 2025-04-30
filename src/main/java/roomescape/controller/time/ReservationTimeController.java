package roomescape.controller.time;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.time.ReservationTimeCreateRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.service.time.ReservationTimeService;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
        @RequestBody ReservationTimeCreateRequest request
    ) {
        return ResponseEntity.ok(reservationTimeService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
