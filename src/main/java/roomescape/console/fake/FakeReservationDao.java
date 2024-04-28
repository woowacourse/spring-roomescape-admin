package roomescape.console.fake;

import java.util.List;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class FakeReservationDao implements ReservationDao {
    private final InMemoryReservationDb inMemoryReservationDb;
    private final InMemoryReservationTimeDb inMemoryReservationTimeDb;

    public FakeReservationDao(InMemoryReservationDb inMemoryReservationDb,
                              InMemoryReservationTimeDb inMemoryReservationTimeDb) {
        this.inMemoryReservationDb = inMemoryReservationDb;
        this.inMemoryReservationTimeDb = inMemoryReservationTimeDb;
    }

    @Override
    public List<Reservation> findAll() {
        return inMemoryReservationDb.selectAll();
    }

    @Override
    public long save(String name, String date, long timeId) {
        ReservationTime reservationTime = inMemoryReservationTimeDb.selectById(timeId);
        return inMemoryReservationDb.insert(name, date, reservationTime);
    }

    @Override
    public boolean deleteById(long id) {
        return inMemoryReservationDb.deleteById(id);
    }
}
