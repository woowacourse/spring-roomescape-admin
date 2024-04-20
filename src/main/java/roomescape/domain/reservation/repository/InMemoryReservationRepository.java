package roomescape.domain.reservation.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDateTime;

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
        Reservation updatedReservation = reservation.updateId(index.getAndIncrement());
        reservations.put(updatedReservation.getId(), updatedReservation);
        return updatedReservation;
    }

    public boolean existsByReservationDateTime(ReservationDateTime reservationDateTime) {
        return reservations.values().stream()
                .anyMatch(r -> r.isSameReservationDateTime(reservationDateTime));
    }

    public Optional<Reservation> findById(long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(r -> reservations.remove(id));
    }
}
