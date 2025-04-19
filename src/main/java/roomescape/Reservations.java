package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        boolean isDuplicate = isDuplicate(reservation);
        if (isDuplicate) {
            throw new IllegalArgumentException("이미 해당 시간에 예약한 이름이 존재합니다.");
        }
        reservations.add(reservation);
    }

    private boolean isDuplicate(final Reservation reservation) {
        return reservations.stream()
                .anyMatch(existing ->
                        existing.getName().equals(reservation.getName()) &&
                                existing.getDate().equals(reservation.getDate()) &&
                                existing.getTime().equals(reservation.getTime())
                );
    }

    public void remove(Long id) {
        Reservation removeReservation = reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 ID의 예약을 찾을 수 없습니다."));

        reservations.remove(removeReservation);
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }
}
