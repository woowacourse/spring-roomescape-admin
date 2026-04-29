package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;

@Service
public class RoomEscapeService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong();

    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    public Reservation create(String name, String date, String time) {
        Reservation reservation = Reservation.create(index.incrementAndGet(), name, date, time);
        reservations.add(reservation);
        return reservation;
    }

    public void delete(final long id) {
        reservations.removeIf(reservation -> reservation.hasId(id));
    }
}
