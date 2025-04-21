package roomescape.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationGroup {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public Long getIndexAndIncrement() {
        return index.getAndIncrement();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteReservationById(Long id) {
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        reservations.remove(deleteReservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
