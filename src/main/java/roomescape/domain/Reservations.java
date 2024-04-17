package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservations() {
        this(new ArrayList<>());
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public Reservation find(Long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean delete(Long id) {
        return reservations.remove(find(id));
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }
}
