package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.dto.reservation.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> reservations() {
        return reservationDao.findAllReservations()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();

        Long id = reservationDao.saveReservation(reservation);
        Reservation saveReservation = reservationDao.findReservation(id);

        return ReservationResponse.from(saveReservation);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }
}
