package roomescape.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(final List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public void add(final Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean isExistById(final Long id) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getId().equals(id));
    }

    public void deleteBy(final Long id) {
        final Reservation target = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        reservations.remove(target);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
