package roomescape.scheduler;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.ReservationEntity;

@Component
public class ReservationScheduler {

    private final ReservationDao reservationDao;

    public ReservationScheduler(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationDao.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse scheduleReservation(ReservationRequest request) {
        Reservation reservation = request.toInstance();
        ReservationEntity entity = reservationDao.addReservation(reservation);
        return ReservationResponse.from(entity);
    }

    public void cancelReservation(Long id) {
        if (!reservationDao.existsById(id)) {
            throw new IllegalArgumentException("id에 해당하는 예약을 찾을 수 없습니다.");
        }
        reservationDao.deleteById(id);
    }

    public void cancelAllReservations() {
        reservationDao.deleteAll();
    }
}
