package roomescape.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.request.ReservationCreateRequest;
import java.util.ArrayList;
import roomescape.dto.response.ReservationResponse;

@RestController
public class ReservationController {

    private final AtomicLong id = new AtomicLong(0);
    private final Reservations reservations = new Reservations(new ArrayList<>());

    @GetMapping("/reservations")
    public List<ReservationResponse> getReservations() {
        return reservations.getReservations()
                .stream()
                .map(ReservationResponse::fromReservation)
                .toList();
    }

    @PostMapping("/reservations")
    public ReservationResponse createReservations(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation = reservationCreateRequest.toReservation(id.incrementAndGet());
        reservations.add(reservation);

        return ReservationResponse.fromReservation(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservations.remove(id);
    }
}
