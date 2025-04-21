package roomescape.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Reservations {
    private List<Reservation> reservations = new ArrayList<>();

    public Reservation deleteReservation(long id) {
        Reservation oldReservation = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("[ERROR] ID(%d)가 일치하는 예약 내역을 찾을 수 없습니다.", id)));
        reservations.remove(oldReservation);
        return oldReservation;
    }

    public boolean addReservation(Reservation reservation) {
        return reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
