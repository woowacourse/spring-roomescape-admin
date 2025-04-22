package roomescape.reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    protected Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservation add(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getAll() {
        return Collections.unmodifiableList(reservations);
    }

    public void deleteById(Long id) {
        reservations.remove(findById(id));
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.hasSame(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 id 입니다."));
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Reservations that = (Reservations) other;
        return Objects.equals(reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reservations);
    }
}
