package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RequestMapping("/times")
@RestController
@RequiredArgsConstructor
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        return ResponseEntity.ok(reservationTimeService.save(reservationTimeRequest));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getAllReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.delete(id);

        return ResponseEntity.ok().build();
    }
}

