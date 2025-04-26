package roomescape.repository;

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
    public Reservation insert(final Reservation reservation) {
        long reservationId = reservationDao.insertReservation(reservation);
        ReservationTime reservationTime = reservationTimeDao.findById(reservation.getTime().getId());
        return new Reservation(reservationId, reservation.getName(), reservation.getDate(), reservationTime);
    }

    @Override
    public void delete(final long id) {
        reservationDao.delete(id);
    }
}
