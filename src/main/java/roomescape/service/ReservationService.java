package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.reservation.request.ReservationRequest;
import roomescape.controller.reservation.response.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse save(final ReservationRequest reservationRequest) {
        final ReservationTime time = reservationTimeDao.findById(reservationRequest.timeId());
        final Reservation reservation = reservationRequest.toReservation(time);
        final long id = reservationDao.save(reservation);
        return ReservationResponse.from(id, reservation);
    }

    public List<ReservationResponse> read() {
        return reservationDao.read();
    }

    public void delete(final Long id) {
        reservationDao.delete(id);
    }
}
