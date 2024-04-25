package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao,
                              final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse createReservation(final ReservationCreateRequest request) {
        final ReservationTime reservationTime = reservationTimeDao.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException(request.timeId() + "에 해당하는 예약 시간 정보가 없습니다."));
        final Reservation reservation = request.toReservation(reservationTime);
        final Long id = reservationDao.save(reservation);
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(), reservationTime);
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    public void deleteReservation(final Long id) {
        reservationDao.deleteById(id);
    }
}
