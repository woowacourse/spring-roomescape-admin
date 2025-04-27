package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> readAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        Reservation reservationWithId = reservationDao.insert(reservation, reservationRequest.timeId());
        return ReservationResponse.from(reservationWithId);
    }

    public void deleteById(long id) {
        reservationDao.deleteById(id);
    }
}
