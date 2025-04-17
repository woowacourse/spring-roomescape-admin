package roomescape.reservation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Reservations {

    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public boolean deleteById(long id) {
        return reservations.removeIf((reservation) -> reservation.getId() == id);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
