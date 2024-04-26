package roomescape.console.fakedao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class FakeReservationDao implements ReservationDao {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public long save(String name, String date, long timeId) {
        long thisId = id.getAndIncrement();
        reservations.put(thisId,
                new Reservation(thisId, name, date, new ReservationTime(timeId, "00:00")));
        return thisId;
    }

    @Override
    public boolean deleteById(long id) {
        boolean exists = reservations.containsKey(id);
        if (exists) {
            reservations.remove(id);
        }
        return exists;
    }
}
