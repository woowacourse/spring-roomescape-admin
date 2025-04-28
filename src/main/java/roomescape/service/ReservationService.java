package roomescape.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.model.ReservationTime;
import roomescape.entity.ReservationWithTimeId;
import roomescape.model.Reservation;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> selectAllReservation() {

        List<Reservation> reservations = reservationDao.selectAllReservation();

        return reservations;
    }

    public Reservation addReservation(ReservationWithTimeId reservationWithTimeId) {

        ReservationTime reservationTime;
        try {
            reservationTime = reservationTimeDao.findTimeById(reservationWithTimeId.getTimeId());
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        Long reservationId = reservationDao.addReservation(reservationWithTimeId);

        return new Reservation(reservationId, reservationWithTimeId.getName(), reservationWithTimeId.getDate(),
                reservationTime);
    }

    public int deleteReservationById(Long id) {
        return reservationDao.deleteReservationById(id);
    }
}
