package roomescape.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationDao;

public class FakeReservationDaoImpl implements ReservationDao {
    private final Map<Long, Reservation> sources = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1L);

    @Override
    public Long save(final Reservation reservation) {
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
    public void deleteById(Long id) {
        sources.remove(id);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(sources.get(id));
    }
}
