package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public List<Reservation> findAll() {
        return reservationDao.readAll();
    }

    public Reservation create(Reservation request) {
        Long id = reservationDao.save(request);
        return Reservation.toEntity(id, request);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
