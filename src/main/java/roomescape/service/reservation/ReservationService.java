package roomescape.service.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.reservation.ReservationDao;
import roomescape.repository.reservationtime.ReservationTimeDao;
import roomescape.service.reservation.request.ReservationServiceRequest;
import roomescape.service.reservation.response.ReservationResponse;

@Service
public final class ReservationService {

    private final ReservationDao jdbcReservationDao;
    private final ReservationTimeDao jdbcReservationTimeDao;

    public ReservationService(final ReservationDao jdbcReservationDao,
                              final ReservationTimeDao jdbcReservationTimeDao) {
        this.jdbcReservationDao = jdbcReservationDao;
        this.jdbcReservationTimeDao = jdbcReservationTimeDao;
    }

    public ReservationResponse save(final ReservationServiceRequest reservationRequest) {
        final ReservationTime time = jdbcReservationTimeDao.findById(reservationRequest.timeId());
        final Reservation reservation = reservationRequest.toReservation(time);
        final long id = jdbcReservationDao.save(reservation);
        return ReservationResponse.from(id, reservation);
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = jdbcReservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public void deleteById(final Long id) {
        jdbcReservationDao.deleteById(id);
    }
}
