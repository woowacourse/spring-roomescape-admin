package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponseDto> findAll() {
        return reservationService.findAll();
    }

    @PostMapping
    public ReservationResponseDto save(@RequestBody final ReservationRequestDto request) {
        return reservationService.save(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        final boolean deleted = reservationService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent()
                                 .build();
        }
        return ResponseEntity.notFound()
                             .build();
    }
}
