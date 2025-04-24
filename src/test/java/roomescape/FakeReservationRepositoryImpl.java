package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

public class FakeReservationRepositoryImpl implements ReservationRepository {
    private final Map<Long, Reservation> sources = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1L);

    @Override
    public long save(final Reservation reservation) {
        long id = this.id.getAndIncrement();
        reservation.setId(id);
        sources.put(id, reservation);

        return id;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(sources.values());
    }

    @Override
    public void deleteById(long id) {
        sources.remove(id);
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return Optional.ofNullable(sources.get(id));
    }
}
