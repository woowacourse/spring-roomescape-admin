package roomescape.reservation.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.dao.ReservationTimeDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;
import roomescape.reservation.dto.request.ReservationRequest;
import roomescape.reservation.dto.response.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException("예약 시간을 찾을 수 없습니다."));

        Reservation reservation = Reservation.from(reservationRequest, reservationTime);

        Reservation savedReservation = reservationDao.insert(reservation);
        return ReservationResponse.from(savedReservation);
    }

    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void removeReservation(Long id) {
        reservationDao.delete(id);
    }
}
