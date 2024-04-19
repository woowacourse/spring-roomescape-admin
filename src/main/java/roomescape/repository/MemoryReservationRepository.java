package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Reservation findById(long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("id에 해당하는 예약을 찾을 수 없습니다: " + id));
    }

    @Override
    public void deleteById(long id) {
        Reservation found = findById(id);
        reservations.remove(found);
    }
}
