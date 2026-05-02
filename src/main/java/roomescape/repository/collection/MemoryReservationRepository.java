package roomescape.repository.collection;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public Reservation save(Reservation reservation) {
        Reservation saved = new Reservation(counter.getAndIncrement(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(saved);
        return saved;
    }

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public void delete(Long id) {
        reservations.removeIf(it -> it.getId().equals(id));
    }
}
