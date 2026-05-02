package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class MemoryReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public List<Reservation> getAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        long id = nextId.getAndIncrement();
        reservations.put(id, reservation.withId(id));

        return reservation.withId(id);
    }

    @Override
    public void deleteById(long reservationId) {
        Reservation removed = reservations.remove(reservationId);
        if (removed == null) {
            throw new NoSuchElementException("존재하지 않는 예약 아이디 입니다. reservationId: " + reservationId);
        }
    }

    @Override
    public boolean existByReservationTimeId(long reservationTimeId) {
        return reservations.values().stream()
            .anyMatch(reservation -> reservation.getTimeId() == reservationTimeId);
    }
}
