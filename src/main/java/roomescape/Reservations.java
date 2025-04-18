package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void save(Reservation entity) {
        reservations.add(entity);
    }

    public void deleteById(Long id) {
        if (!reservations.removeIf(reservation -> reservation.getId().equals(id))) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }
}
