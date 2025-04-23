package roomescape.reservation.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.entity.ReservationEntity;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationEntity> getAll() {
        return reservations.entrySet().stream()
                .map((entry)-> ReservationEntity.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public ReservationEntity put(final Reservation reservation) {
        long id = index.getAndIncrement();
        reservations.put(id, reservation);
        return ReservationEntity.of(id, reservation);
    }

    @Override
    public void deleteById(final long id) {
        reservations.remove(id);
    }

    @Override
    public Optional<ReservationEntity> findById(final long id) {
        return Optional.empty();
    }
}
