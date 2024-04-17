package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();

    public void save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    public void deleteById(Long id) {
        Reservation target = reservations.get(id);
        reservations.remove(target);
    }
}
