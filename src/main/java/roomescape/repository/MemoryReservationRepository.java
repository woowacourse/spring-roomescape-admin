package roomescape.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1);

    public Reservation save(final Reservation reservation) {
        Reservation newReservation = reservation.withId(index.getAndIncrement());
        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteById(final Long id) {
        reservations.removeIf(reservation -> reservation.isEqualId(id));
    }

    public Optional<Reservation> findById(final Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isEqualId(id)).findFirst();
    }

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }
}
