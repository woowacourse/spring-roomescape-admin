package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Reservations;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> getReservations() {
        Reservations reservations = reservationDao.findAll();
        return reservations.getReservations().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public ReservationResponse saveReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());

        Reservation newReservation = request.toReservation(null, reservationTime);
        Reservation savedReservation = reservationDao.save(newReservation);

        return new ReservationResponse(savedReservation);
    }

    public boolean deleteReservation(Long id) {
        return reservationDao.deleteById(id);
    }
}
