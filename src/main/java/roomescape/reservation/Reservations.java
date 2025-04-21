package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;

public class Reservations {
    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getAll() {
        return reservations;
    }

    public void add(Reservation newReservation) {
        reservations.add(newReservation);
    }

    public void removeById(Long id) {
        reservations.remove(findById(id));
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다"));
    }
}
