package roomescape.reservation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.service.utils.ReservationMapper;
import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.domain.ReservationTime;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao, ReservationMapper reservationMapper) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
        this.reservationMapper = reservationMapper;
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservation = reservationMapper.toReservation(reservationRequest, reservationTime);
        return reservationMapper.toReservationResponse(reservationDao.insert(reservation));
    }

    public List<ReservationResponse> findAllReservations() {
        return reservationMapper.toReservationResponses(reservationDao.findAll());
    }

    public void deleteReservationById(long id) {
        reservationDao.delete(id);
    }
}
