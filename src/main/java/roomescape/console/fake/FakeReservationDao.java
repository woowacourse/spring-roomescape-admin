package roomescape.console.fake;

import java.util.List;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class FakeReservationDao implements ReservationDao {
    private final FakeReservationDb fakeReservationDb;
    private final FakeReservationTimeDb fakeReservationTimeDb;

    public FakeReservationDao(FakeReservationDb fakeReservationDb,
                              FakeReservationTimeDb fakeReservationTimeDb) {
        this.fakeReservationDb = fakeReservationDb;
        this.fakeReservationTimeDb = fakeReservationTimeDb;
    }

    @Override
    public List<Reservation> findAll() {
        return fakeReservationDb.selectAll();
    }

    @Override
    public long save(String name, String date, long timeId) {
        ReservationTime reservationTime = fakeReservationTimeDb.selectById(timeId);
        return fakeReservationDb.insert(name, date, reservationTime);
    }

    @Override
    public boolean deleteById(long id) {
        return fakeReservationDb.deleteById(id);
    }
}
