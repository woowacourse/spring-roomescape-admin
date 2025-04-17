package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {

    private final AtomicLong nextId = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    public Reservation save(final Reservation reservation) {
        final Reservation reservationWithId = reservation.writeId(nextId.getAndIncrement());
        reservations.add(reservationWithId);

        return reservationWithId;
    }

    public void removeReservation(final long id) {
        final Reservation target = findBy(id);

        reservations.remove(target);
    }

    private Reservation findBy(long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR]"));
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }
}
