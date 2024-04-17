package roomescape.dao;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FakeReservationDao implements ReservationDao {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationEntity> findAll() {
        return reservations.entrySet().stream()
                .map(entry -> new ReservationEntity(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public ReservationEntity save(Reservation reservation) {
        long id = index.getAndIncrement();
        reservations.put(id, reservation);
        return new ReservationEntity(id, reservation);
    }

    @Override
    public boolean existsById(final long id) {
        return reservations.keySet().stream().anyMatch(key -> key == id);
    }

    @Override
    public void deleteById(final long id) {
        reservations.remove(id);
    }
}
