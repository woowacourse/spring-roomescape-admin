package roomescape.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(final List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public void add(final Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteById(final Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    private Reservation findById(final Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 id가 없습니다."));
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
