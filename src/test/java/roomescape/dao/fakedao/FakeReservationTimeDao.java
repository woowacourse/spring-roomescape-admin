package roomescape.dao.fakedao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.dao.EmptyResultDataAccessException;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeDao {
    private final Map<Long, ReservationTime> reservationTimes = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1);


    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.values()
                .stream()
                .toList();
    }

    @Override
    public ReservationTime findById(long id) {
        if (reservationTimes.containsKey(id)) {
            return reservationTimes.get(id);
        }
        throw new EmptyResultDataAccessException(1);
    }

    @Override
    public long save(String startAt) {
        long thisId = id.getAndIncrement();
        reservationTimes.put(thisId, new ReservationTime(thisId, startAt));
        return thisId;
    }

    @Override
    public boolean deleteById(long id) {
        boolean exists = reservationTimes.containsKey(id);
        if (exists) {
            reservationTimes.remove(id);
        }
        return exists;
    }
}
