package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(1);

    @GetMapping
    public List<ReservationResponse> read() {
        return reservations.stream()
                .map(ReservationResponse::toDto)
                .toList();
    }

    @PostMapping
    public ReservationResponse create(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = reservationRequest.toReservation(id.getAndIncrement());
        reservations.add(newReservation);
        return ReservationResponse.toDto(newReservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(target -> Objects.equals(target.getId(), id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        reservations.remove(reservation);
    }
}
