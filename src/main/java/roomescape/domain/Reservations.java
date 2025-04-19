package roomescape.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public synchronized void add(final Reservation reservation) {
        reservations.add(reservation);
    }

    public synchronized void deleteById(final Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    private Reservation findById(final Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 id에 대한 예약 기록이 존재하지 않습니다."));
    }

    public synchronized List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
