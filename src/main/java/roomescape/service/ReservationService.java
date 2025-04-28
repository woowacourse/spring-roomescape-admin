package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> readAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservationWithTime = reservationDao.insert(reservation, reservationTime);
        return ReservationResponse.from(reservationWithTime);
    }

    public void deleteById(long id) {
        reservationDao.deleteById(id);
    }
}
