package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> store = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Long id = index.incrementAndGet();
        Reservation saved = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
        store.add(saved);
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        store.removeIf(r -> r.getId().equals(id));
    }
}
