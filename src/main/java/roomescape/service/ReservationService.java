package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationDao;

@Repository
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation saveReservation(final ReservationRequest reservationRequest) {
        long reservationId = reservationDao.save(reservationRequest);
        return getReservation(reservationId);
    }

    private Reservation getReservation(final long id) {
        return reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 예약 조회 결과가 없습니다."));
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.getAll();
    }

    public void deleteReservation(final long id) {
        reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 삭제할 예약 데이터가 없습니다."));
        reservationDao.delete(id);
    }
}
