package roomescape.dao;

import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.database.ReservationInMemoryDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao {

    private final ReservationInMemoryDatabase inMemoryDatabase;

    public ReservationDao(ReservationInMemoryDatabase inMemoryDatabase) {
        this.inMemoryDatabase = inMemoryDatabase;
    }

    public Reservation select(Long id) {
        return inMemoryDatabase.select(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Reservation> selectAll() {
        return inMemoryDatabase.selectAll();
    }

    public Reservation insert(String name, LocalDate date, LocalTime time) {
        return inMemoryDatabase.insert(name, date, time);
    }

    public void delete(Long id) {
        inMemoryDatabase.delete(select(id));
    }

}
