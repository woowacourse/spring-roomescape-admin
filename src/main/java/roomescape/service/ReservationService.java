package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

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
        String startAt = reservationDao.findStartTimeByTimeId(timeId);
        ReservationTime reservationTime = new ReservationTime(timeId, startAt);
        return new Reservation(request.getName(), request.getDate(), reservationTime);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
