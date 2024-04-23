package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTimeResponseDto create(@RequestBody final ReservationTimeRequestDto request) {
        return reservationTimeService.create(request);
    }

    @GetMapping
    public List<ReservationTimeResponseDto> findAll() {
        return reservationTimeService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        final boolean deleted = reservationTimeService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent()
                                 .build();
        }
        return ResponseEntity.notFound()
                             .build();
    }
}
