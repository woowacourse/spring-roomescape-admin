package roomescape.console.fake;

import java.util.List;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeDao {
    private final FakeReservationDb fakeReservationDb;
    private final FakeReservationTimeDb fakeReservationTimeDb;

    public FakeReservationTimeDao(FakeReservationDb fakeReservationDb,
                                  FakeReservationTimeDb fakeReservationTimeDb) {
        this.fakeReservationDb = fakeReservationDb;
        this.fakeReservationTimeDb = fakeReservationTimeDb;
    }

    @Override
    public List<ReservationTime> findAll() {
        return fakeReservationTimeDb.selectAll();
    }

    @Override
    public ReservationTime findById(long id) {
        return fakeReservationTimeDb.selectById(id);
    }

    @Override
    public long save(String startAt) {
        return fakeReservationTimeDb.insert(startAt);
    }

    @Override
    public boolean deleteById(long id) {
        boolean deleted = fakeReservationTimeDb.deleteById(id);
        fakeReservationDb.deleteByTimeId(id);
        return deleted;
    }
}
