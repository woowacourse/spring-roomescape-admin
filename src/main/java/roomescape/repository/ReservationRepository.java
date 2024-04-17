package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public void save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    public void deleteById(Long id) {
        Reservation target = reservations.get(id);
        reservations.remove(target);
    }
}
