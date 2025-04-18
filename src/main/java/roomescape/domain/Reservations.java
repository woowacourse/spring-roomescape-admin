package roomescape.domain;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.exception.CannotAddException;
import roomescape.exception.CannotRemoveException;

public class Reservations {

    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);

    public Reservations(final List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation addReservation(final Reservation reservation) {
        if (existsSameDateTime(reservation)) {
            throw new CannotAddException("[ERROR] 이미 존재하는 예약 시간입니다.");
        }
        Reservation saved = reservation.withId(index.getAndIncrement());
        reservations.add(saved);
        return saved;
    }

    public void removeReservationById(final long id) {
        Reservation found = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny()
                .orElseThrow(() -> new CannotRemoveException("[ERROR] 존재하지 않는 예약 번호입니다: " + id));
        reservations.remove(found);
    }

    private boolean existsSameDateTime(final Reservation reservation) {
        return reservations.stream()
                .anyMatch(reservation::isSameDateTime);
    }
}
