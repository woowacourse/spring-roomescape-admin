package roomescape.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.business.ReservationTimeService;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.ReservationTimeSaveRequest;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeSaveRequest request) {
        var reservationTime = request.toModel();
        return ResponseEntity.ok(reservationTimeService.create(reservationTime));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
