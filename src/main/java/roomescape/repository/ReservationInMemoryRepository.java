package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(1L);

    private final List<Reservation> reservations;

    public ReservationInMemoryRepository() {
        reservations = new ArrayList<>();
    }

    public ReservationInMemoryRepository(List<Reservation> reservations) {
        this.reservations = new ArrayList<>(reservations);
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public Reservation save(Reservation reservation) {
        if (reservation.getId() == null) {
            Long id = ATOMIC_LONG.getAndIncrement();
            reservation = new Reservation(id, reservation.getName(),
                    reservation.getDate(),
                    reservation.getTime());
        }
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        reservations.remove(reservation);
    }
}
