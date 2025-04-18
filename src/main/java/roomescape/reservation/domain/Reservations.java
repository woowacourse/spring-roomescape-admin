package roomescape.reservation.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst();
    }
}
