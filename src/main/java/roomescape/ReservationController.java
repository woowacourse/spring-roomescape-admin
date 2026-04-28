package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0L);

    @PostMapping
    public Reservation addReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(index.incrementAndGet(), reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
        reservations.add(reservation);

        return reservation;
    }
}
