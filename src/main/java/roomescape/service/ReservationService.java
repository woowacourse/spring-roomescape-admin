package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> readReservation() {
        return reservationDao.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public ReservationResponse postReservation(ReservationRequest request) {
        Reservation newReservation = reservationDao.save(request.toEntity(), request.timeId());
        return ReservationResponse.from(newReservation);
    }

    @Transactional
    public void deleteReservation(long id) {
        reservationDao.deleteById(id);
    }
}
