package roomescape.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.dto.ReservationTimeRequest;
import roomescape.domain.dto.ReservationTimeResponse;
import roomescape.domain.service.ReservationTimeService;

import java.util.List;

@RequestMapping("/times")
@RestController
@RequiredArgsConstructor
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@Valid @RequestBody ReservationTimeRequest reservationTimeRequest) {
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

