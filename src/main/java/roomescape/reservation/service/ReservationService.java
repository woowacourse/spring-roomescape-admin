package roomescape.reservation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public Reservation insert(ReservationRequest reservationRequest) {
        return reservationDao.insert(reservationRequest);
    }

    public Reservation findById(long id) {
        return reservationDao.findById(id);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public void delete(long id) {
        reservationDao.delete(id);
    }
}
