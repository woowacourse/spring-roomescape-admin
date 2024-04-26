package roomescape.console.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import roomescape.general.db.ReservationDao;
import roomescape.general.domain.Reservation;

@Primary
@Repository
public class ReservationDaoInMemoryImpl implements ReservationDao {
    private final List<Reservation> memory = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public Reservation save(final Reservation reservation) {
        final Reservation newReservation = new Reservation(
                index.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime());
        memory.add(newReservation);
        return newReservation;
    }

    @Override
    public boolean isTimeIdExist(final long timeId) {
        return memory.stream()
                .anyMatch(reservation -> reservation.getReservationTime().getId() == timeId);
    }

    @Override
    public List<Reservation> findAll() {
        return memory;
    }

    @Override
    public void deleteById(final long id) {
        memory.removeIf(reservation -> reservation.getId() == id);
    }
}
