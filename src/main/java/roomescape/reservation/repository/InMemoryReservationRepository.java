package roomescape.reservation.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> getAll() {
        return reservations.values().stream()
                .toList();
    }

    @Override
    public Reservation put(Reservation reservation) {
        long id = index.getAndIncrement();
        Reservation newReservation = Reservation.toEntity(reservation, id);
        reservations.put(id, newReservation);
        return newReservation;
    }

    @Override
    public void deleteById(final Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("요청한 id와 일치하는 예약 정보가 없습니다.");
        }
        reservations.remove(id);
    }
}
