package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService, ReservationService reservationService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> add(@RequestBody ReservationTimeRequestDto requestDto) {
        return ResponseEntity.ok(reservationTimeService.add(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> findAll() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
