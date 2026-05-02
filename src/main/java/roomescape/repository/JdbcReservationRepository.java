package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcReservationDao jdbcReservationDao;

    public JdbcReservationRepository(JdbcReservationDao jdbcReservationDao) {
        this.jdbcReservationDao = jdbcReservationDao;
    }

    @Override
    public List<ReservationJoinedDto> findAllJoinedDto() {
        return jdbcReservationDao.findAll();
    }

    @Override
    public Reservation findById(long id) {
        return jdbcReservationDao.findById(id);
    }

    @Override
    public ReservationJoinedDto findJoinedDtoById(long id) {
        return jdbcReservationDao.findJoinedDtoById(id);
    }

    @Override
    public long save(Reservation reservation) {
        return jdbcReservationDao.insert(reservation);
    }

    @Override
    public void deleteById(long id) {
        jdbcReservationDao.deleteById(id);
    }
}
