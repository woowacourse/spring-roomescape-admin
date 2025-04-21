package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public final class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public Reservation createReservation(final String name, final LocalDate date, final LocalTime time) {
        final Reservation reservation = new Reservation(index.incrementAndGet(), name, date, time);
        reservations.add(reservation);
        return reservation;
    }

    public void deleteReservationById(final Long id) {
        final Reservation reservation = reservations.stream()
                .filter(value -> Objects.equals(value.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] id를 찾을 수 없습니다."));
        reservations.remove(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
