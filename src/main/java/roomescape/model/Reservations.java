package roomescape.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteById(Id id) {
        Reservation reservation = reservations.stream()
                .filter(reserve -> reserve.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id값이 존재하지 않습니다."));
        reservations.remove(reservation);
    }

    public List<Reservation> getAll() {
        return Collections.unmodifiableList(reservations);
    }
}
