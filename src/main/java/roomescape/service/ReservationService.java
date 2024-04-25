package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationDao;

@Repository
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService() {
        this.reservationDao = new ReservationDao();
    }

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation saveReservation(final ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toEntity();
        return reservationDao.save(reservation);
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationDao.getAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void deleteReservation(final long id) {
        reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 삭제할 예약 데이터가 없습니다."));
        reservationDao.delete(id);
    }
}
