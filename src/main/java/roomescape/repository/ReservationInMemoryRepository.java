package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exceptions.EntityNotFoundException;

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
            long id = ATOMIC_LONG.getAndIncrement();
            reservation = new Reservation(id, reservation.getName(),
                    reservation.getDate(),
                    reservation.getTime());
        }
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("예약 데이터를 찾을 수 없습니다:" + id));
        reservations.remove(reservation);
    }
}
