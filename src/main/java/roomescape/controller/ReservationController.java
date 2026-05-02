package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDetailDto;
import roomescape.dto.ReservationSaveDto;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDetailDto>> getReservations() {
        List<ReservationDetailDto> responseData = reservationService.readAll()
                .stream()
                .map(ReservationDetailDto::from)
                .toList();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDetailDto> createReservation(@RequestBody ReservationSaveDto dto) {
        Reservation savedReservation = reservationService.reserve(dto);
        ReservationDetailDto responseData = ReservationDetailDto.from(savedReservation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseData);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.cancel(id);
        return ResponseEntity.ok().build();
    }

}
