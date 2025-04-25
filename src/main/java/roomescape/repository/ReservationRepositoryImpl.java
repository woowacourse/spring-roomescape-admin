package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationRepositoryImpl(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Override
    public Reservation insert(final String name, final LocalDate date, final long timeId) {
        long reservationId = reservationDao.insertReservation(name, date, timeId);
        ReservationTime reservationTime = reservationTimeDao.findById(timeId);
        return new Reservation(reservationId, name, date, reservationTime);
    }

    @Override
    public void delete(final long id) {
        reservationDao.delete(id);
    }
}
