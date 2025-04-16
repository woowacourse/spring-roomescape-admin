package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void remove(Long id) {
        Reservation removeReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 ID의 예약을 찾을 수 없습니다."));

        reservations.remove(removeReservation);
    }
}
