package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final ReservationDao reservationDao;

    public JdbcReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public List<ReservationJoinedDto> findAllJoinedDto() {
        return reservationDao.findAll();
    }

    @Override
    public Reservation findById(long id) {
        return reservationDao.findById(id);
    }

    @Override
    public ReservationJoinedDto findJoinedDtoById(long id) {
        return reservationDao.findJoinedDtoById(id);
    }

    @Override
    public long save(Reservation reservation) {
        return reservationDao.insert(reservation);
    }

    @Override
    public void deleteById(long id) {
        reservationDao.deleteById(id);
    }
}
