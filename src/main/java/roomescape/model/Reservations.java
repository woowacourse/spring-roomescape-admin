package roomescape.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteById(Id id) {
        Reservation reservation = reservations.stream()
                .filter(reserve -> reserve.isSameId(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);
    }

    public List<Reservation> getAll() {
        return Collections.unmodifiableList(reservations);
    }
}
