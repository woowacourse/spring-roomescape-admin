package roomescape.dao;

import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.database.ReservationInMemoryDatabase;

import java.util.List;

@Repository
public class InMemoryReservationDao implements ReservationDao{

    private final ReservationInMemoryDatabase inMemoryDatabase;

    public InMemoryReservationDao(ReservationInMemoryDatabase inMemoryDatabase) {
        this.inMemoryDatabase = inMemoryDatabase;
    }

    public Reservation select(Long id) {
        return inMemoryDatabase.select(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Reservation> selectAll() {
        return inMemoryDatabase.selectAll();
    }

    public Reservation insert(Reservation reservation) {
        return inMemoryDatabase.insert(reservation);
    }

    public void delete(Long id) {
        inMemoryDatabase.delete(select(id));
    }

}
