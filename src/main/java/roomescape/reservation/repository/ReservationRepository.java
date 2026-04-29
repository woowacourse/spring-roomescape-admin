package roomescape.reservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
@AllArgsConstructor
public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private static AtomicLong id = new AtomicLong(1L);

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation addReservation(Reservation reservation) {
        Long createdId = id.getAndIncrement();
        Reservation createdReservation = new Reservation(
                createdId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
        reservations.add(createdReservation);
        return createdReservation;
    }

    public void deleteById(Long id) {
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 ID의 예약이 없습니다."));

        reservations.remove(deleteReservation);
    }
}
