package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst();
    }

    public void remove(Reservation reservation) {
        reservations.remove(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
