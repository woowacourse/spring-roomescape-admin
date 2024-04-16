package roomescape.domain.reservation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();

    public InMemoryReservationRepository() {
        save(new Reservation(1L, "user1", "2021-07-01", "09:00"));
    }

    @Override
    public void save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream()
                .sorted(Comparator.comparing(Reservation::getId))
                .toList();
    }
}
