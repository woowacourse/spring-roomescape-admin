package roomescape.user.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.user.domain.Reservation;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(1);

    public Long save(final Reservation reservation) {
        final Long currentId = id.getAndIncrement();
        
        final Reservation created = new Reservation(
                currentId, reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(created);

        return created.getId();
    }

    public Optional<Reservation> findById(final Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    public void delete(final Reservation reservation) {
        reservations.remove(reservation);
    }

    public void clear() {
        reservations.clear();
    }
}
