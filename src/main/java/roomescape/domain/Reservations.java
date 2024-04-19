package roomescape.domain;

import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void delete(long id) {
        Reservation reservation = find(id);
        reservations.remove(reservation);
    }

    private Reservation find(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations);
    }
}
