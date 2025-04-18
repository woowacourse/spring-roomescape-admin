package roomescape.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {

    private final List<Reservation> reservations;
    private final AtomicLong reservationIndex = new AtomicLong(1);

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation save(Person person, ReservationTime reservationTime) {
        Reservation reservation = new Reservation(reservationIndex.getAndIncrement(), person, reservationTime);
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    private Reservation findById(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.sameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약번호 입니다."));
    }
}
