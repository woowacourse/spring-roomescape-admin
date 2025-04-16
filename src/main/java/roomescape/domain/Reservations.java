package roomescape.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(final List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public void add(final Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteBy(final Long id) {
        final Optional<Reservation> target = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
        target.ifPresent(reservations::remove);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
