package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class MemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    public MemoryReservationRepository() {
        this.reservations = new ConcurrentSkipListMap<>();
        this.index = new AtomicLong();
    }

    @Override
    public Reservation save(Reservation reservationRequest) {
        Reservation newReservation = new Reservation(index.incrementAndGet(), reservationRequest);
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    @Override
    public Optional<Integer> deleteById(Long id) {
        if (reservations.containsKey(id)) {
            reservations.remove(id);
            return Optional.of(1);
        } else {
            return Optional.empty();
        }
    }
}
