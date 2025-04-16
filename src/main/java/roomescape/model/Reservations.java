package roomescape.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeById(Long id) {
        Reservation removeReservation = findById(id);
        reservations.remove(removeReservation);
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
