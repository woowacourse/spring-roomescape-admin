package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation addReservation(Reservation reservation) {
        Long timeId = reservation.getTime().getId();
        ReservationTime reservationTime = reservationTimeDao.findById(timeId);
        Reservation reservationForAdd = new Reservation(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservationTime
        );

        return reservationDao.add(reservationForAdd);
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public void deleteReservationById(long reservationId) {
        reservationDao.deleteById(reservationId);
    }
}
