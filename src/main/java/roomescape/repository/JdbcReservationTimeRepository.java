package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcReservationTimeDao jdbcReservationTimeDao;

    public JdbcReservationTimeRepository(JdbcReservationTimeDao jdbcReservationTimeDao) {
        this.jdbcReservationTimeDao = jdbcReservationTimeDao;
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcReservationTimeDao.findAll();
    }

    @Override
    public ReservationTime findById(long id) {
        return jdbcReservationTimeDao.findById(id);
    }

    @Override
    public long save(LocalTime startAt) {
        return jdbcReservationTimeDao.insert(startAt);
    }

    @Override
    public void deleteById(long id) {
        jdbcReservationTimeDao.deleteById(id);
    }
}
