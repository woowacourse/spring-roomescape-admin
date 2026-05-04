package roomescape.service.fake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public boolean isExistsByTimeId(long id) {
        return reservations.values()
                .stream()
                .anyMatch(reservation -> reservation.getTime().getId() == id);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation findById(long id) {
        return reservations.get(id);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation saved = new Reservation(id.getAndIncrement(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
        reservations.put(saved.getId(), saved);
        return saved;
    }

    @Override
    public void delete(long id) {
        reservations.remove(id);
    }

    @Override
    public boolean isExists(long id) {
        return reservations.containsKey(id);
    }
}
