package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong id = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public List<ReservationResponseDto> getReservations() {
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping
    public ReservationResponseDto saveReservation(
            @RequestBody final ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = reservationRequestDto.toEntity(id.getAndIncrement());
        final ReservationResponseDto reservationResponseDto = ReservationResponseDto.from(reservation);
        reservations.add(reservation);
        return reservationResponseDto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        final Optional<Reservation> findReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny();
        if (findReservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservations.remove(findReservation.get());
        return ResponseEntity.ok().build();
    }
}
