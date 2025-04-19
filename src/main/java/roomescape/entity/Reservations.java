package roomescape.entity;

import java.util.ArrayList;
import java.util.List;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public void saveReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void validateReservationTimeAvailability(Reservation reservation) {
        boolean isDuplicate = reservations.stream()
                .anyMatch(existing -> existing.isSameDateTime(reservation));

        if (isDuplicate) {
            throw new IllegalArgumentException("[ERROR] 이미 예약되었어요. 다른 날짜를 골라주세요.");
        }
    }

    public List<Reservation> findAllReservations() {
        return reservations;
    }
}
