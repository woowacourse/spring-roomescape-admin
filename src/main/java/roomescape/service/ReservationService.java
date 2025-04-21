package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse findById(long id) {
        Reservation reservation = reservationDao.findById(id);

        return ReservationResponse.from(reservation);
    }

    public ReservationResponse save(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());
        Reservation reservation = request.toReservation(reservationTime);
        Reservation saved = reservationDao.save(reservation);

        return ReservationResponse.from(saved);
    }

    public void deleteById(long id) {
        reservationDao.deleteById(id);
    }
}
