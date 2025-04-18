package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservations {
    private final List<ReservationEntity> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void save(ReservationEntity reservation) {
        reservations.add(reservation);
    }

    public void deleteById(Long id) {
        if (!reservations.removeIf(reservation -> reservation.id().equals(id))) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    public List<ReservationEntity> findAll() {
        return Collections.unmodifiableList(reservations);
    }
}
