package roomescape;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class ReservationManager {

    private final AtomicLong index = new AtomicLong();

    public Reservation createReservation(final ReservationCreateRequest request) {
        return new Reservation(index.incrementAndGet(), request.name(), request.date(), request.time());
    }
}
