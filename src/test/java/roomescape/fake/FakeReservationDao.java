package roomescape.fake;

import roomescape.dao.ReservationDao;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationDao implements ReservationDao {

    private static final ReservationTime TEST_TIME = ReservationTime.of(LocalTime.now());

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1L);

    public FakeReservationDao() {
        insertInitialValues();
    }

    private void insertInitialValues() {
        Reservation reservation1 = Reservation.of("듀이", LocalDate.now(), TEST_TIME);
        Reservation reservation2 = Reservation.of("범블비", LocalDate.now(), TEST_TIME);
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
