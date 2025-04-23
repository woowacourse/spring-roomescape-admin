package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.reservation.ReservationDao;
import roomescape.dao.resetvationTime.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationCreateResponse create(ReservationCreateRequest reservationCreateRequest) {
        ReservationTime time = reservationTimeDao.findById(reservationCreateRequest.timeId());
        return new ReservationCreateResponse(
                reservationDao.create(reservationCreateRequest), reservationCreateRequest.name(),
                reservationCreateRequest.date(), time);
    }

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll().stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();
    }

    public void delete(final Long id) {
        reservationDao.delete(id);
    }
}
