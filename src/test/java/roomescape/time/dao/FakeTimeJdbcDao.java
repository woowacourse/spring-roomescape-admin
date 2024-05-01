package roomescape.time.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import roomescape.time.domain.Time;

public class FakeTimeJdbcDao implements TimeDao {

    private final Map<Long, Time> fakeDataBase = new HashMap<>();

    private Long id = 1L;

    public FakeTimeJdbcDao() {
    }

    @Override
    public Time save(Time reservationTime) {
        reservationTime.setId(id);
        fakeDataBase.put(id, reservationTime);
        id += 1L;
        return reservationTime;
    }

    @Override
    public List<Time> findAllOrderByReservationTime() {
        return fakeDataBase.values().stream()
                .toList();
    }

    @Override
    public Time findById(long reservationTimeId) {
        return fakeDataBase.get(reservationTimeId);
    }

    @Override
    public void deleteById(long reservationTimeId) {
        fakeDataBase.remove(reservationTimeId);
    }
}
