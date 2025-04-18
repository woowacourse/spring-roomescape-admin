package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void save(Reservation newReservation) {
        if (reservations.stream().anyMatch(reservation -> reservation.isDuplicatedWith(newReservation))) {
            throw new IllegalArgumentException("이미 예약이 존재하는 날짜입니다.");
        }
        reservations.add(newReservation);
    }

    public void deleteById(final Long id) {
        if (!reservations.removeIf(reservation -> reservation.isSameId(id))) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }
}
