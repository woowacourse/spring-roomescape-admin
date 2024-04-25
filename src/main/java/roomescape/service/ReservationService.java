package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.dao.H2ReservationDao;
import roomescape.dao.H2ReservationTimeDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final H2ReservationDao h2ReservationDao;
    private final H2ReservationTimeDao h2ReservationTimeDao;

    public ReservationService(H2ReservationDao h2ReservationDao, H2ReservationTimeDao h2ReservationTimeDao) {
        this.h2ReservationDao = h2ReservationDao;
        this.h2ReservationTimeDao = h2ReservationTimeDao;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = h2ReservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse save(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = h2ReservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toReservation(reservationTime);
        long id = h2ReservationDao.save(reservation);
        return findById(id);
    }

    private ReservationResponse findById(long id) {
        Reservation reservation = h2ReservationDao.findById(id);
        return ReservationResponse.of(reservation);
    }

    public void deleteById(long id) {
        int isDelete = h2ReservationDao.deleteById(id);
        if (isDelete < 1) {
            throw new IllegalArgumentException("해당 id는 존재하지 않습니다.");
        }
    }
}
