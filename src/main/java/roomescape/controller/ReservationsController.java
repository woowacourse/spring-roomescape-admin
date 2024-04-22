package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationsController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(1);

    @GetMapping("reservations")
    public List<ReservationResponse> read() {
        return reservations.stream()
                .map(ReservationResponse::toDto)
                .toList();
    }

    @PostMapping("reservations")
    public ReservationResponse create(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = reservationRequest.toReservation(id.getAndIncrement());
        reservations.add(newReservation);
        return ReservationResponse.toDto(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(target -> Objects.equals(target.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);
    }
}
