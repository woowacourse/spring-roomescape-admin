package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public void save(Reservation reservation) {
        reservation.initializeId(index.getAndIncrement());
        reservations.put(reservation.getId(), reservation);
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
