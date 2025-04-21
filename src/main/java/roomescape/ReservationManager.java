package roomescape;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.controller.dto.ReservationRequest;

@Component
public class ReservationManager {

    private final AtomicLong index = new AtomicLong();

    public Reservation createReservation(final ReservationRequest request) {
        return new Reservation(index.incrementAndGet(), request.name(), request.date(), request.time());
    }
}
