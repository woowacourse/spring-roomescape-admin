package roomescape.repository;

import org.springframework.stereotype.Component;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

@Component
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;

    public ReservationRepositoryImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Reservations findAll() {
        return new Reservations(reservationDao.findAll());
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    @Override
    public boolean deleteById(Long id) {
        return reservationDao.deleteById(id);
    }
}
