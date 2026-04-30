package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationDao.getReservations();

        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        Long reservationId = reservationDao.createReservation(request);
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());
        Reservation reservation = new Reservation(reservationId, request.name(), request.date(), reservationTime);

        return ReservationResponse.of(reservation);
    }

    public void deleteReservation(long id) {
        reservationDao.deleteReservation(id);
    }
}
