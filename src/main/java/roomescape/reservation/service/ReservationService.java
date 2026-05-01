package roomescape.reservation.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.reservation.controller.dto.CreateReservationRequest;
import roomescape.reservation.controller.dto.ReservationResponse;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.dao.ReservationTimeDao;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.exception.ReservationTimeException;
import roomescape.reservationtime.exception.ReservationTimeExceptionCode;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    //TODO: 예약하려는 시간에 예약이 존재하는 지 검증 추가
    public ReservationResponse save(CreateReservationRequest request) {
        request.validate();
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId())
                .orElseThrow(() -> new ReservationTimeException(ReservationTimeExceptionCode.RESERVATION_NOT_EXISTS));

        Reservation reservation = request.toReservation(reservationTime);
        Reservation savedReservation = reservationDao.save(reservation);
        return new ReservationResponse(savedReservation, reservationTime);
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAllReservations();
    }

    public void delete(Long reservationId) {
        Optional<Reservation> reservation = reservationDao.findById(reservationId);
        reservation.ifPresent(reservationDao::delete);
    }
}
