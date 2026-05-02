package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.service.ReservationCommand;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcReservationDao jdbcReservationDao;

    public JdbcReservationRepository(JdbcReservationDao jdbcReservationDao) {
        this.jdbcReservationDao = jdbcReservationDao;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcReservationDao.findAll();
    }

    @Override
    public Reservation findById(long id) {
        return jdbcReservationDao.findById(id);
    }

    @Override
    public long save(ReservationCommand reservationCommand) {
        return jdbcReservationDao.insert(reservationCommand);
    }

    @Override
    public void deleteById(long id) {
        jdbcReservationDao.deleteById(id);
    }
}
