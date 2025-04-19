package roomescape.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;
    private final Counter counter;

    public Reservations(final List<Reservation> reservations, final Counter counter) {
        this.reservations = reservations;
        this.counter = counter;
    }

    public Reservations() {
        this(new ArrayList<>(), new Counter());
    }

    public Reservation addReservation(final String name, final LocalDateTime dateTime) {
        final Reservation reservation = new Reservation(counter.increase(), name, dateTime);
        reservations.add(reservation);
        return reservation;
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
