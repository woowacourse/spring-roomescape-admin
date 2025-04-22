package roomescape.dao;

import roomescape.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationDao implements ReservationDao {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1L);

    public FakeReservationDao() {
        insertInitialValues();
    }

    private void insertInitialValues() {
        Reservation reservation1 = Reservation.of("듀이", LocalDateTime.now());
        Reservation reservation2 = Reservation.of("범블비", LocalDateTime.now());
        insert(reservation1);
        insert(reservation2);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream()
                .toList();
    }

    @Override
    public Reservation insert(final Reservation reservation) {
        Long savedId = index.getAndIncrement();
        reservations.put(savedId, reservation);
        return reservations.get(savedId);
    }

    @Override
    public boolean deleteById(final Long id) {
        Reservation removedReservation = reservations.remove(id);
        return removedReservation != null;
    }
}
