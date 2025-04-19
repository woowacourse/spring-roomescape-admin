package roomescape.reservation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Reservations {

    private final List<Reservation> values = new ArrayList<>();

    public void add(Reservation reservation) {
        values.add(reservation);
    }

    public Reservation findById(Long id) {
        return values.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다"));
    }

    public List<Reservation> findAll() {
        return values;
    }

    public void deleteById(Long id) {
        Reservation findReservation = findById(id);
        values.remove(findReservation);
    }
}
