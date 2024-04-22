package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.db.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;


@Service
public class ReservationService {

    @Autowired
    ReservationDao reservationDao;

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Long create(@RequestBody ReservationRequest reservationRequest) {
        return reservationDao.createReservation(reservationRequest);
    }

    public Reservation findById(final Long id) {
        return reservationDao.findById(id);
    }

    public void deleteById(final long id) {
        reservationDao.deleteById(id);
    }
}
