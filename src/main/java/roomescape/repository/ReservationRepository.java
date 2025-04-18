package roomescape.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.model.Reservation;

@Component
public class ReservationRepository {

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());

    private AtomicLong index = new AtomicLong(3);

    public ReservationRepository() {
        List<Reservation> initialReservations = List.of(
                Reservation.createReservation(1L, "브라운", LocalDateTime.of(2024, 4, 1, 10, 0)),
                Reservation.createReservation(2L, "솔라", LocalDateTime.of(2024, 4, 1, 11, 0)),
                Reservation.createReservation(3L, "브리", LocalDateTime.of(2024, 4, 2, 14, 0))
        );
        reservations.addAll(initialReservations);
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public Long add(Reservation reservation) {
        long currentIndex = index.incrementAndGet();
        Reservation newReservation = Reservation.createReservationWithId(currentIndex, reservation);
        reservations.add(newReservation);
        return currentIndex;
    }

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.sameId(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다."));
    }

    public void deleteById(Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

}
