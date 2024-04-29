package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationDao.readAll();
        return ReservationResponse.toReservationsResponse(reservations);
    }

    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = makeReservationByRequest(request);
        Long id = reservationDao.save(reservation);
        Reservation entity = Reservation.toEntity(id, reservation);
        return ReservationResponse.toResponse(entity);
    }

    private Reservation makeReservationByRequest(ReservationRequest request) {
        Long timeId = request.getTimeId();
        String startAt = reservationTimeDao.findStartTimeByTimeId(timeId);
        ReservationTime reservationTime = new ReservationTime(timeId, startAt);
        return new Reservation(request.getName(), request.getDate(), reservationTime);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
