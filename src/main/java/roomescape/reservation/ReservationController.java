package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.request.ReservationRequest;
import roomescape.reservation.dto.response.ReservationResponse;

@Slf4j
@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return this.reservations;
    }

    @PostMapping("/reservations")
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toDomain(index.getAndIncrement());
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        log.info("Deleting reservation with id {}", id);
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
