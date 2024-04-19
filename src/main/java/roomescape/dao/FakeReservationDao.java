package roomescape.dao;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.db.FakeReservationsDb;
import roomescape.domain.Reservation;

@Component
public class FakeReservationDao implements ReservationDao {
    private final FakeReservationsDb fakeReservationsDb = new FakeReservationsDb();

    @Override
    public List<Reservation> findAll() {
        return fakeReservationsDb.readAll();
    }

    @Override
    public void save(Reservation reservation) {
        fakeReservationsDb.create(reservation);
    }

    @Override
    public boolean deleteById(long id) {
        return fakeReservationsDb.delete(id);
    }
}
