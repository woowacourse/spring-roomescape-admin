package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.Reservation;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse findById(long id) {
        Reservation reservation = reservationDao.findById(id);
        return ReservationResponse.of(reservation);
    }

    public long save(ReservationRequest reservationRequest) {
        return reservationDao.save(reservationRequest);
    }

    public void deleteById(long id) {
        reservationDao.deleteById(id);
    }
}
