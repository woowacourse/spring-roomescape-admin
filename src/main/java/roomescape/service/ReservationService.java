package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation save(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());
        Reservation reservation = request.toEntity(reservationTime);
        Long reservationId = reservationDao.save(reservation);
        return new Reservation(reservationId, reservation.getName(), reservation.getDate(), reservationTime);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public void delete(Long reservationId) {
        reservationDao.deleteById(reservationId);
    }
}
