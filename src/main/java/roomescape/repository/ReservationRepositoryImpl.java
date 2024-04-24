package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.data.vo.Reservation;
import roomescape.data.vo.Reservation.Builder;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationRepositoryImpl(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public long add(final Reservation reservation) {
        final var reservationTime = reservationTimeDao.findOne(reservation.getTimeId());
        final var saveReservation = new Builder()
                .time(reservationTime)
                .name(reservation.getName())
                .id(reservation.getId())
                .date(reservation.getDate())
                .build();

        return reservationDao.save(saveReservation);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationDao.findAll();
    }

    @Override
    public Reservation get(long id) {
        return reservationDao.findOne(id);
    }

    @Override
    public void remove(long id) {
        reservationDao.delete(id);
    }
}
