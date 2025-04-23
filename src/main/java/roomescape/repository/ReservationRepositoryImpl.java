package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Counter;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

@Service
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;

    public ReservationRepositoryImpl(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Reservations findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return new Reservations(reservations, new Counter());
    }
}
