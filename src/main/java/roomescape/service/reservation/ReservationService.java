package roomescape.service.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.reservation.request.ReservationServiceRequest;
import roomescape.service.reservation.response.ReservationResponse;

@Service
public final class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse save(final ReservationServiceRequest reservationRequest) {
        final ReservationTime time = reservationTimeDao.findById(reservationRequest.timeId());
        final Reservation reservation = reservationRequest.toReservation(time);
        final long id = reservationDao.save(reservation);
        return ReservationResponse.from(id, reservation);
    }

    public List<ReservationResponse> readAll() {
        List<Reservation> reservations = reservationDao.readAll();
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public void deleteBy(final Long id) {
        reservationDao.delete(id);
    }
}
