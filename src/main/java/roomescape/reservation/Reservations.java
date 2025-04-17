package roomescape.reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public final class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public Reservation add(ReservationDto reservationDto) {
        Reservation newReservation = new Reservation(
                reservationDto.name(),
                reservationDto.date(),
                reservationDto.time());
        reservations.add(newReservation);
        return newReservation;
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
}
