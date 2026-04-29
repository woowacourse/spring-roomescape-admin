package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@Service
@Transactional
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation enrollReservation(String name, String date, Long timeId) {
        ReservationTime targetTime = reservationTimeDao.findById(timeId);
        return reservationDao.save(
                Reservation.constructWithNoId(name, date, targetTime)
        );
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public void deleteSpecificReservationById(Long targetId) {
        reservationDao.delete(targetId);
    }
}
