package roomescape.domain;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Reservations {

    private final Queue<Reservation> reservations;

    public Reservations() {
        this.reservations = new ConcurrentLinkedQueue<>();
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
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 id에 대한 예약 기록이 존재하지 않습니다."));
    }

    public Queue<Reservation> getReservations() {
        return new ConcurrentLinkedQueue<>(reservations);
    }
}
