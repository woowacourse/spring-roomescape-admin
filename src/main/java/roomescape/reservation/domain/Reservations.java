package roomescape.reservation.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {

    private final List<Reservation> reservations;
    private final AtomicLong reservationId = new AtomicLong();

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void create(Reservation created) {
        reservations.add(created);
    }

    public Long generateId() {
        return reservationId.incrementAndGet();
    }

    public void delete(Reservation target) {
        reservations.remove(target);
    }

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
