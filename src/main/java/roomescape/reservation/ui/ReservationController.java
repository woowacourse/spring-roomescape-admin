package roomescape.reservation.ui;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.ui.dto.ReservationRequestDto;
import roomescape.reservation.ui.dto.ReservationResponseDto;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        final List<ReservationResponseDto> reservations = reservationService.getReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping()
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody @Valid final ReservationRequestDto reservationRequestDto) {
        final ReservationResponseDto reservationResponseDto = reservationService.createReservation(reservationRequestDto);
        return ResponseEntity.ok(reservationResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable final long id) {
        reservationService.delete(id);
        return ResponseEntity.ok(null);
    }
}
