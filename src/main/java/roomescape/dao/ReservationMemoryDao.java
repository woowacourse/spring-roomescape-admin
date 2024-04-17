package roomescape.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.dao.entity.Reservation;

@Repository
public class ReservationMemoryDao implements ReservationDao {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(Reservation reservation) {
        reservation.setId(counter.incrementAndGet());
        reservations.add(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
