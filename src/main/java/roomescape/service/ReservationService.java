package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public void delete(final long id) {
        reservationDao.delete(id);
    }

    public Reservation add(final ReservationRequest reservationRequest) {
       return reservationDao.add(reservationRequest);
    }
}
