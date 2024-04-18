package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong autoIncreaseId = new AtomicLong(1);

    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    public Reservation create(Reservation reservation) {
        Reservation createdReservation = reservation.toEntity(autoIncreaseId.getAndIncrement());
        reservations.add(createdReservation);
        return createdReservation;
    }

    public void deleteById(Long id) {
        Reservation searchedReservation = findById(id);
        reservations.remove(searchedReservation);
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("해당 예약이 저장되어 있지 않습니다."));
    }

}
