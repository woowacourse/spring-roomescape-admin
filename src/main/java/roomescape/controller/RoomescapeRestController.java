package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

@RestController
public class RoomescapeRestController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/reservations")
    public List<ReservationResponse> reservations() {
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostMapping("/reservations")
    public ReservationResponse addReservationInfo(@RequestBody Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, counter.incrementAndGet());
        reservations.add(newReservation);
        return new ReservationResponse(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservationInfo(@PathVariable Long id) {
        reservations.removeIf(reservation -> reservation.getId().getId().equals(id));
    }
}
