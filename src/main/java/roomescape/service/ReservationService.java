package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> readReservations() {
        return reservationDao.findAll();
    }

    public Reservation create(ReservationCreateRequest request, ReservationTime reservationTime) {
        return reservationDao.insert(request, reservationTime);
    }

    public void delete(long id) {
        reservationDao.delete(id);
    }
}
