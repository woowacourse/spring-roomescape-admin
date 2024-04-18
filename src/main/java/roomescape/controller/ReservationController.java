package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> findAll() {
        List<ReservationResponseDto> reservationResponseDtos = reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();

        return ResponseEntity.ok(reservationResponseDtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> add(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation newReservation = new Reservation(
                index.incrementAndGet(),
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationRequestDto.time()
        );
        reservations.add(newReservation);

        return ResponseEntity.ok(ReservationResponseDto.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservations.stream()
                .filter(reservation -> reservation.hasId(id))
                .findFirst()
                .ifPresent(reservations::remove);

        return ResponseEntity.ok().build();
    }
}
