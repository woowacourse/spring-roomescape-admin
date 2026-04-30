package roomescape.domain.reservations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.entity.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final Map<Long, ReservationTime> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public ReservationTime save(ReservationTime reservation) {
        if (reservation.getId() == null) {
            ReservationTime saved = ReservationTime.of(
                    sequence++,
                    reservation.getStartAt()
            );
            store.put(saved.getId(), saved);
            return saved;
        }
        return reservation;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ReservationTime> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
