package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDTO;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeRequestDTO requestDTO) {
        ReservationTime newReservationTime = reservationTimeService.createReservationTime(requestDTO);

        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> read() {
        return ResponseEntity.ok().body(reservationTimeService.findAllReservationTime());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);

        return ResponseEntity.ok().build();
    }
}
