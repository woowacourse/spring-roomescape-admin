package roomescape.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong increment = new AtomicLong(1);

    public Reservation add(final Reservation reservation) {
        Reservation newReservation = new Reservation(increment.getAndIncrement(), reservation);
        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteById(final Long id) {
        reservations.removeIf(reservation -> reservation.isEqualId(id));
    }

    public boolean existReservation(final Long id) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.isEqualId(id));
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
