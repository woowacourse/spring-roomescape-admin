package roomescape.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public Long addReservation(final String name, final ReservationDateTime reservationDateTime) {
        long reservationId = index.getAndIncrement();
        Reservation reservation = new Reservation(reservationId, name, reservationDateTime);
        reservations.add(reservation);
        return reservationId;
    }

    public void removeById(final Long id) {
        Reservation removeReservation = findById(id);
        reservations.remove(removeReservation);
    }

    public Reservation findById(final Long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

}
