package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.model.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
public final class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @GetMapping("/reservations")
    public List<ReservationResponseDto> reservations() {
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> reserve(@RequestBody ReservationRequestDto dto) {
        try {
            Reservation reservation = dto.toEntity(id.getAndIncrement());
            reservations.add(reservation);
            return ResponseEntity.ok(ReservationResponseDto.from(reservation));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/reservations/{id}")
    public void cancel(@PathVariable int id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
