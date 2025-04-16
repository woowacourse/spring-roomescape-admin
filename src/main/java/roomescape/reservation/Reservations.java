package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {
    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();


    public Reservation saveReservation(final Reservation reservation) {
        final Reservation indexedReservation = new Reservation(index.getAndIncrement(), reservation);
        reservations.add(indexedReservation);

        return indexedReservation;
    }

    public void removeReservation(final long id) {
        final Reservation target = findBy(id);

        reservations.remove(target);
    }

    private Reservation findBy(long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR]"));
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }
}
