package roomescape.step4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.step4.domain.ReservationTime;
import roomescape.step4.dto.ReservationTimeRequest;
import roomescape.step4.dto.ReservationTimeResponse;
import roomescape.step4.service.ReservationTimeService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/step4/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> saveReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTime reservationTime = reservationTimeService.saveReservationTime(request);
        ReservationTimeResponse response = ReservationTimeResponse.from(reservationTime);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAllReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.findAllReservationTimes();
        List<ReservationTimeResponse> response = reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        try {
            reservationTimeService.deleteReservationTime(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
