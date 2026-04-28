package roomescape.dao;

import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.database.ReservationInMemoryDatabase;

import java.util.List;

@Repository
public class ReservationDao {

    private final ReservationInMemoryDatabase inMemoryDatabase;

    public ReservationDao(ReservationInMemoryDatabase inMemoryDatabase) {
        this.inMemoryDatabase = inMemoryDatabase;
    }

    public List<Reservation> selectAll() {
        return inMemoryDatabase.selectAll();
    }

}
