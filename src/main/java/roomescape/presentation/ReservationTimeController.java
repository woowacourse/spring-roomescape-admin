package roomescape.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.business.ReservationTimeService;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.ReservationTimeSaveRequest;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservationTime(@RequestBody ReservationTimeSaveRequest request) {
        var reservationTime = request.toModel();
        var savedReservationTimeResponse = reservationTimeService.create(reservationTime);
        return ResponseEntity.created(URI.create("/times/" + savedReservationTimeResponse.id())).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.getReservationTimes());
    }
}
