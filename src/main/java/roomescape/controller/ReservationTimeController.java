package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import java.util.List;

@Controller
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeCreateRequest request) {
        return ResponseEntity.ok(reservationTimeService.createReservationTime(request));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> readAllReservationTime() {
        return ResponseEntity.ok(reservationTimeService.readAllReservationTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
