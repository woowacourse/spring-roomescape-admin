package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationService reservationService;

    public ReservationTimeController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTime(@Valid @RequestBody final ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeResponse response = reservationService.createReservationTime(reservationTimeRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> readTimes() {
        List<ReservationTimeResponse> timeResponses = reservationService.findAllReservationTimes();
        return ResponseEntity.ok().body(timeResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable final Long id) {
        reservationService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        reservationService.deleteAllReservationTimes();
        return ResponseEntity.ok().build();
    }
}
