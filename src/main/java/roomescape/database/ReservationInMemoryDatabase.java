package roomescape.database;

import org.springframework.stereotype.Component;
import roomescape.Reservation;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ReservationInMemoryDatabase {

    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();
    private final AtomicLong autoIncrement = new AtomicLong(0);

    public List<Reservation> selectAll() {
        return Collections.unmodifiableList(reservations);
    }

}
