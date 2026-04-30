package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> readAll() {
        return ResponseEntity.ok(reservationTimeService.findAll());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> create(@RequestBody ReservationTimeRequestDto requestDto) {
        return ResponseEntity.ok(reservationTimeService.save(requestDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationTimeService.deleteById(id);
    }
}
