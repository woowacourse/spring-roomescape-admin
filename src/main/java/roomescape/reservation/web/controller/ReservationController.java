package roomescape.reservation.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.web.dto.ReservationCreateDto;
import roomescape.reservation.web.dto.ReservationDto;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController()
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> findAllReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(
            @RequestBody ReservationCreateDto request
    ) {
        return ResponseEntity.ok(reservationService.saveReservation(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
