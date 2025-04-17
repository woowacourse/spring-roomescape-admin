package roomescape.user.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.user.domain.Reservation;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private Long id = 1L;

    public synchronized Long save(final Reservation reservation) {
        final Reservation created = new Reservation(
                id, reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(created);
        increaseId();

        return created.getId();
    }

    private void increaseId() {
        id = id + 1;
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
