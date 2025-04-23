package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationTimeResponse;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createTime(@Valid @RequestBody ReservationTimeCreateRequest request) {
        return ResponseEntity.ok().body(reservationTimeService.addReservationTime(request));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        return ResponseEntity.ok(reservationTimeService.getAllReservationTimes());
    }

    @GetMapping("/times/{id}")
    public ResponseEntity<ReservationTimeResponse> getTimeById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationTimeService.getReservationTimeById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTimeById(id);
    }
}
