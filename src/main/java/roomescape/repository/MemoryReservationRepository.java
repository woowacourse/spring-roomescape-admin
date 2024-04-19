package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> readAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation saved = new Reservation(
                index.getAndIncrement(),
                reservation.getName(),
                reservation.getStartDate(),
                reservation.getStartTime());
        reservations.add(saved);
        return saved;
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny();
    }

    @Override
    public void deleteById(long id) {
        Optional<Reservation> found = findById(id);
        if (found.isEmpty()) {
            throw new IllegalArgumentException("해당하는 아이디를 찾을 수 없습니다");
        }
        reservations.remove(found.get());
    }
}
