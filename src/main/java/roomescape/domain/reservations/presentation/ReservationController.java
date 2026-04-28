package roomescape.domain.reservations.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservations.entity.Reservation;
import roomescape.domain.reservations.presentation.dto.ReservationRequest;
import roomescape.domain.reservations.presentation.dto.ReservationResponse;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public ReservationResponse addReservation(
            @RequestBody ReservationRequest request
    ) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(),
                request.name(),
                request.date(),
                request.time()
        );
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> getReservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(
            @PathVariable Long id
    ) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
