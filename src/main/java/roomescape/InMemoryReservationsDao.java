package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class InMemoryReservationsDao implements ReservationsDao {

    private final List<Reservation> reservations;
    private final AtomicLong index;

    public InMemoryReservationsDao() {
        this.reservations = new ArrayList<>();
        this.index =  new AtomicLong(0);
    }

    public List<Reservation> getReservationsInfo() {
        return reservations;
    }
}
