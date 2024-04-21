package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationDto;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("reservations")
public class ReservationApiController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping
    public List<Reservation> reservations() {
        return reservations;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody final ReservationDto reservationDto) {
        Reservation reservation = Reservation.toEntity(index.getAndIncrement(), reservationDto);
        reservations.add(reservation);
        return reservation;
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable final Long id) {
        final Reservation reservation = reservations.stream()
                                                    .filter(it -> Objects.equals(it.getId(), id))
                                                    .findFirst()
                                                    .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);
    }
}
