package roomescape.domain.reservation.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private static final long INITIAL_VALUE = 1L;

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    public InMemoryReservationRepository() {
        this.reservations = new ConcurrentSkipListMap<>();
        this.index = new AtomicLong(INITIAL_VALUE);
    }

    @Override
    public Reservation save(Reservation reservation) {
        validateReservationDateTimeExists(reservation);
        Reservation updatedReservation = reservation.updateId(index.getAndIncrement());
        reservations.put(updatedReservation.getId(), updatedReservation);
        return updatedReservation;
    }

    private void validateReservationDateTimeExists(Reservation reservation) {
        boolean existsDateTime = reservations.values().stream()
                .anyMatch(r -> r.isSameReservationDateTime(reservation));
        if (existsDateTime) {
            throw new IllegalArgumentException("이미 예약된 날짜, 시간입니다.");
        }
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    @Override
    public void deleteById(Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("존재하지 않은 id입니다.");
        }
        reservations.remove(id);
    }
}
