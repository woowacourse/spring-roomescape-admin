package roomescape.domain;

import java.util.List;
import java.util.Objects;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public Reservation findBy(Long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(id + "는 존재하지 않습니다."));
    }

    public void remove(Long id) {
        reservations.remove(findBy(id));
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
