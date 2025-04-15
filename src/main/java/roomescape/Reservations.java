package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {

    private List<Reservation> reservations;

    public Reservations(final List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
