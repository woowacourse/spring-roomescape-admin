package roomescape.user.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.exception.DataNotFoundException;
import roomescape.user.domain.Reservation;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {

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

    public Reservation getOneById(final Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("해당 예약 정보가 존재하지 않습니다. id = " + id));
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
