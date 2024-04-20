package roomescape.model;

import java.util.ArrayList;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();

    public Reservations() {
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void delete(long id) {
        Reservation delReservation = reservations.stream()
                .filter(reservation -> reservation.isSameReservationId(id))
                .findFirst()
                .orElseThrow();
        reservations.remove(delReservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
