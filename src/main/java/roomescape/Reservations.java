package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Reservations {

    private List<Reservation> reservations;

    public Reservations(final List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public void add(final Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteBy(final Long id) {
        reservations = reservations.stream()
                .filter(reservation -> !reservation.getId().equals(id))
                .collect(Collectors.toList());
    }
}
