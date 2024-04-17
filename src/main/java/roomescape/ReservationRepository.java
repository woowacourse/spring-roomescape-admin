package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong autoIncreaseId = new AtomicLong(1);

    public List<Reservation> readReservations() {
        return List.copyOf(reservations);
    }

    public Reservation createReservation(Reservation reservation) {
        Reservation createdReservation = reservation.toEntity(autoIncreaseId.getAndIncrement());
        reservations.add(createdReservation);
        return createdReservation;
    }

    public void deleteReservation(Long id) {
        Reservation searchedReservation = readReservation(id);

        reservations.remove(searchedReservation);
    }

    private Reservation readReservation(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.id().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

}
