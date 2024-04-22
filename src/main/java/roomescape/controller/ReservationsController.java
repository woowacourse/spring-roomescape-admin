package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRequestDto;
import roomescape.domain.ReservationResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationsController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(1);

    @GetMapping("reservations")
    public ResponseEntity<List<ReservationResponseDto>> read() {
        List<ReservationResponseDto> dtos = reservations.stream()
                .map(ReservationResponseDto::toDto)
                .toList();
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation newReservation = reservationRequestDto.toEntity(id.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.ok().body(ReservationResponseDto.toDto(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(target -> Objects.equals(target.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);
        return ResponseEntity.ok().build();
    }
}
