package roomescape.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dao.ReservationDao;
import roomescape.entity.Reservation;

public class FakeReservationDao implements ReservationDao {

    private final Map<Long, Reservation> fakeReservationDB = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return fakeReservationDB.values().stream().toList();
    }

    @Override
    public Reservation findById(long id) {
        return fakeReservationDB.get(id);
    }

    @Override
    public long save(Reservation reservation) {
        long idx = index.getAndIncrement();
        fakeReservationDB.put(idx, new Reservation(idx, reservation.getName(), reservation.getDate(), reservation.getTime()));
        return idx;
    }

    @Override
    public int deleteById(long id) {
        Reservation reservation = fakeReservationDB.remove(id);
        if (reservation != null) {
            return 1;
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        fakeReservationDB.clear();
    }
}
