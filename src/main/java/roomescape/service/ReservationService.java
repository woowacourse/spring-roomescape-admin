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

    public Reservation addReservation(ReservationRequest reservationRequest) {
        ReservationTime findReservationTime = reservationTimeDao.findById(reservationRequest.timeId());
        Long id = reservationDao.insert(reservationRequest);
        return Reservation.toEntity(id, reservationRequest, findReservationTime);
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAllReservations();
    }

    public void removeReservation(Long id) {
        reservationDao.delete(id);
    }
}
