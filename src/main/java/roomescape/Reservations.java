package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public Reservation addAndGet(Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation, index.incrementAndGet());
        reservations.add(newReservation);
        return newReservation;
    }

    public void delete(Long id) {
        Reservation selectedReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 예약 id는 존재하지 않습니다."));
        reservations.remove(selectedReservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations.stream()
                .map(reservation -> new Reservation(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime()))
                .toList();
    }
}
