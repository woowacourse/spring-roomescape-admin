package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("reservations")
public class ReservationApiController {

    private final Reservations reservations = new Reservations();
    private final AtomicLong reservationId = new AtomicLong();

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservations.getReservations().stream()
                .map(ReservationResponse::fromReservation)
                .toList();
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest request) {
        Reservation created = request.toReservation(reservationId.incrementAndGet());
        reservations.add(created);

        return ReservationResponse.fromReservation(created);
    }

    @DeleteMapping("{id}")
    public void deleteReservation(@PathVariable Long id, HttpServletResponse response) {
        Optional<Reservation> target = reservations.findById(id);

        if (target.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        reservations.remove(target.get());
    }
}
