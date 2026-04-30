package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> readAll() {
        return reservationDao.selectAll();
    }

    public Reservation reserve(String name, LocalDate date, Long timeId) {
        ReservationTime reservationTime = readReservationTime(timeId);
        return reservationDao.insert(new Reservation(name, date, reservationTime));
    }

    public void cancel(Long id) {
        reservationDao.delete(id);
    }

    private ReservationTime readReservationTime(Long id) {
        return reservationTimeDao.select(id)
                .orElseThrow(IllegalArgumentException::new);
    }

}
