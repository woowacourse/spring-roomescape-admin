package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation add(String name, String date, String time) {
        return reservationDao.save(
                Reservation.constructWithNoId(name, date, time)
        );
    }

    public List<Reservation> find() {
        return reservationDao.findAll();
    }

    public void delete(Long targetId) {
        reservationDao.delete(targetId);
    }
}
