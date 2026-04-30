package roomescape.service.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.ReservationRepository;

public class StubReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> store = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Reservation save(Reservation reservation) {
        long id = index.getAndIncrement();

        Reservation saved = Reservation.of(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        store.put(id, saved);
        return saved;
    }

    @Override
    public void deleteById(long id) {
        store.remove(id);
    }

}
