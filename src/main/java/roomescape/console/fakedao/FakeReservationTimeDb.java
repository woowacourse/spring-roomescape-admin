package roomescape.console.fakedao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.dao.EmptyResultDataAccessException;
import roomescape.domain.ReservationTime;

public class FakeReservationTimeDb {
    private static final FakeReservationTimeDb instance = new FakeReservationTimeDb();
    private final Map<Long, ReservationTime> reservationTimes = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    public static FakeReservationTimeDb getInstance() {
        return instance;
    }

    public List<ReservationTime> selectAll() {
        return reservationTimes.values()
                .stream()
                .toList();
    }

    public ReservationTime selectById(long id) {
        if (reservationTimes.containsKey(id)) {
            return reservationTimes.get(id);
        }
        throw new EmptyResultDataAccessException(1);
    }

    public long insert(String startAt) {
        long thisId = id.getAndIncrement();
        reservationTimes.put(thisId, new ReservationTime(thisId, startAt));
        return thisId;
    }

    public boolean deleteById(long id) {
        boolean exists = reservationTimes.containsKey(id);
        if (exists) {
            reservationTimes.remove(id);
        }
        return exists;
    }
}
