package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.Reservation;
import roomescape.ReservationTime;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

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

    public ReservationResponse save(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toReservation(reservationTime);
        long id = reservationDao.save(reservation);
        return findById(id);
    }

    private ReservationResponse findById(long id) {
        Reservation reservation = reservationDao.findById(id);
        return ReservationResponse.of(reservation);
    }

    public void deleteById(long id) {
        int isDelete = reservationDao.deleteById(id);
        if (isDelete < 1) {
            throw new IllegalArgumentException("해당 id는 존재하지 않습니다.");
        }
    }
}
