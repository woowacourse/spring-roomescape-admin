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

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public Reservation createReservation(ReservationRequest request) {
        Long generatedId = reservationDao.save(request);
        ReservationTime time = reservationTimeDao.findById(request.timeId());

        return request.toEntity(generatedId, time);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
