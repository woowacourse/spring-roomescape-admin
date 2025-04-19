package roomescape.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservations {

    private static final String IS_DUPLICATE_TIME = "[EXCEPTION] 해당 시간대는 이미 예약되어 있습니다.";
    private static final String INVALID_ID = "[EXCEPTION] 해당 아이디는 존재하지 않습니다.";

    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public List<Reservation> findReservations() {
        return reservations.stream().toList();
    }

    public Reservation saveReservation(Reservation reservation) {
        checkDuplicates(reservation);
        reservations.add(reservation);
        return reservation;
    }

    public void deleteReservationById(Long id) {
        Reservation reservation = reservations.stream()
                .filter(reserve -> Objects.equals(reserve.id(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ID));
        reservations.remove(reservation);
    }

    private void checkDuplicates(Reservation reservation) {
        boolean isDuplicate = reservations.stream()
                .anyMatch(reserve -> reserve.date().equals(reservation.date()) &&
                        reserve.time().equals(reservation.time())
                );

        if (isDuplicate) {
            throw new IllegalArgumentException(IS_DUPLICATE_TIME);
        }
    }
}
