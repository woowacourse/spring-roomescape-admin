package roomescape.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private static final AtomicLong AUTO_INCREMENT = new AtomicLong(1);
    private static final Map<Long, Reservation> REPOSITORY = new ConcurrentHashMap<>();

    @Override
    public List<Reservation> getAll() {
        return REPOSITORY.values().stream()
                .toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        Long saveId = AUTO_INCREMENT.getAndIncrement();
        reservation.updateId(saveId);
        REPOSITORY.put(saveId, reservation);
        return Reservation.deepCopyOf(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(REPOSITORY.get(id))
                .map(Reservation::deepCopyOf);
    }

    @Override
    public void remove(Long id) {
        REPOSITORY.remove(id);
    }
}
