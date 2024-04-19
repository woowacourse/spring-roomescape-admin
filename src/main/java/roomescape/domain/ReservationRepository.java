package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations;
    private final AtomicLong index;

    public ReservationRepository() {
        reservations = new ArrayList<>();
        index = new AtomicLong(1L);
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public Reservation findById(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 id(%d)의 예약이 존재하지 않습니다.".formatted(id)));
    }

    public Reservation create(Reservation reservation) {
        Reservation createdReservation = new Reservation(index.getAndIncrement(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
        reservations.add(createdReservation);
        return createdReservation;
    }

    public void remove(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void removeAll() {
        reservations.clear();
        index.set(1L);
    }
}
