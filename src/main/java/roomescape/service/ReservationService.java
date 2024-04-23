package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        ReservationTime time = reservationTimeDao.find(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toReservation(time);
        Reservation savedReservation = reservationDao.create(reservation);
        return ReservationResponse.toResponse(savedReservation);
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationDao.getAll();
        return ReservationResponse.toResponses(reservations);
    }

    public void deleteReservation(long id) {
        reservationDao.delete(id);
    }
}
