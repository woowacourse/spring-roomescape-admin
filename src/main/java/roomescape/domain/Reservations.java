package roomescape.domain;

import java.util.ArrayList;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservations() {
        this(new ArrayList<>());
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }
}
