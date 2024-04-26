package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.web.dto.ReservationRequest;
import roomescape.web.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> findAllReservation() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse saveReservation(ReservationRequest request) {
        ReservationTime time = findReservationTimeById(request.timeId());
        Reservation reservation = request.toReservation(time);
        reservationDao.save(reservation);
        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = findReservationById(id);
        reservationDao.delete(reservation);
    }

    private Reservation findReservationById(Long id) {
        return reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
    }

    private ReservationTime findReservationTimeById(Long id) {
        return reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
    }
}
