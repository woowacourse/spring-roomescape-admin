package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation create(String name, String date, Long timeId) {
        ReservationTime time = reservationTimeDao.findBy(timeId);
        Reservation reservation = new Reservation(null, name, date, time);
        Long id = reservationDao.insert(reservation);
        return reservationDao.findBy(id);
    }

    public void delete(Long id) {
        int deletedCount = reservationDao.delete(id);
        if (deletedCount != 1) {
            throw new IllegalArgumentException("[ERROR] 삭제 요청 실패");
        }
    }
}
