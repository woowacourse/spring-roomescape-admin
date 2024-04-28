package roomescape.console.fake;

import java.util.List;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeDao {
    private final InMemoryReservationDb inMemoryReservationDb;
    private final InMemoryReservationTimeDb inMemoryReservationTimeDb;

    public FakeReservationTimeDao(InMemoryReservationDb inMemoryReservationDb,
                                  InMemoryReservationTimeDb inMemoryReservationTimeDb) {
        this.inMemoryReservationDb = inMemoryReservationDb;
        this.inMemoryReservationTimeDb = inMemoryReservationTimeDb;
    }

    @Override
    public List<ReservationTime> findAll() {
        return inMemoryReservationTimeDb.selectAll();
    }

    @Override
    public ReservationTime findById(long id) {
        return inMemoryReservationTimeDb.selectById(id);
    }

    @Override
    public long save(String startAt) {
        return inMemoryReservationTimeDb.insert(startAt);
    }

    @Override
    public boolean deleteById(long id) {
        boolean deleted = inMemoryReservationTimeDb.deleteById(id);
        inMemoryReservationDb.deleteByTimeId(id);
        return deleted;
    }
}
