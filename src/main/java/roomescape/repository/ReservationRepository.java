package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1L);

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    public Reservation save(Reservation reservation) {
        Reservation saved = new Reservation(index.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
        reservations.add(saved);
        return saved;
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
