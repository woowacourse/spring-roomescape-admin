package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createTime(@Valid @RequestBody ReservationTimeCreateRequest request) {
        return ResponseEntity.ok().body(reservationTimeService.addReservationTime(request));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getTimes() {
        return ResponseEntity.ok(reservationTimeService.getAllReservationTimes());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTimeById(id);
    }
}
