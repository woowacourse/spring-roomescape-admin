package roomescape.reservation.domain;

import java.util.ArrayList;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void create(Reservation created) {
        reservations.add(created);
    }

    public void delete(Reservation target) {
        reservations.remove(target);
    }
}
