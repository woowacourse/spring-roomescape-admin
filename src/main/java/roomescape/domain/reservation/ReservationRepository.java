package roomescape.domain.reservation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final AtomicLong sequence = new AtomicLong(0);
    private final Map<Long, Reservation> reservations;

    public ReservationRepository() {
        this.reservations = new LinkedHashMap<>();
    }

    public Reservation save(Reservation reservation) {
        long id = sequence.incrementAndGet();
        Reservation reservationWithId = Reservation.createWithId(id, reservation);
        reservations.put(id, reservationWithId);
        return reservationWithId;
    }

    public List<Reservation> findAll() {
        return reservations.values().stream()
            .filter(reservation -> !reservation.isDeleted())
            .toList();
    }

    public Optional<Reservation> findReservation(Long id) {
        return Optional.ofNullable(reservations.get(id));
    }
}
