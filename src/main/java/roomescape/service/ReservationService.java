package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimesDao;
import roomescape.dao.ReservationsDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationsDao reservationDao;
    private final ReservationTimesDao reservationTimesDao;

    public ReservationService(
        ReservationsDao reservationDao,
        ReservationTimesDao reservationTimesDao
    ) {
        this.reservationDao = reservationDao;
        this.reservationTimesDao = reservationTimesDao;
    }

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll()
            .stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimesDao.findById(request.timeId());
        Reservation reservation = new Reservation(request.name(), request.date(), reservationTime);
        return ReservationResponse.from(reservationDao.save(reservation));
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
