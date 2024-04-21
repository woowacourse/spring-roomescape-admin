package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        long id = reservationDao.create(reservation);
        return ReservationResponse.toResponse(id, reservationRequest);
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationDao.getAll();
        return ReservationResponse.toResponses(reservations);
    }

    public void deleteReservation(long id) {
        reservationDao.delete(id);
    }
}
