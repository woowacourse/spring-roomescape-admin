package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryReservationRepositoryImpl implements ReservationRepository {
    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(0);

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Reservation save(final Reservation reservation) {
        Reservation newReservation = new Reservation(index.incrementAndGet(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);

        return newReservation;
    }

    @Override
    public void deleteById(final long id) {
        reservations.stream()
                .filter(reservation -> reservation.hasId(id))
                .findFirst()
                .ifPresent(reservations::remove);
    }

    void deleteAll() {
        reservations.clear();
    }
}
