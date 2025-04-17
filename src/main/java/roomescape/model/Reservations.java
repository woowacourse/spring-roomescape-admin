package roomescape.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteById(Long id) {
        Reservation reservation = reservations.stream()
                .filter(reserve -> Objects.equals(reserve.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);
    }

    public List<Reservation> getAll() {
        return Collections.unmodifiableList(reservations);
    }
}
