package roomescape.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

@Component
public class ReservationInMemoryDao implements ReservationDao {

    private final Map<Long, Reservation> reservations;
    private final AtomicLong idCount;

    public ReservationInMemoryDao() {
        this.reservations = new ConcurrentHashMap<>();
        idCount = new AtomicLong(1L);
    }

    @Override
    public ReservationEntity addReservation(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity(idCount.getAndIncrement(), reservation);
        reservations.put(entity.id(), reservation);
        return entity;
    }

    @Override
    public List<ReservationEntity> findAll() {
        return reservations.entrySet()
                .stream()
                .map(entry -> new ReservationEntity(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        reservations.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return reservations.containsKey(id);
    }

    @Override
    public void deleteAll() {
        reservations.clear();
    }
}
