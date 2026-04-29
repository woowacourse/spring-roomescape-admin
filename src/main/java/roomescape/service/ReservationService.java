package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationDao;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> readAllReservation() {
        return ReservationResponse.from(reservationDao.findAllReservation());
    }

    public ReservationResponse createReservation(ReservationCreateRequest request) {
        Long id = reservationDao.insertReservation(Reservation.from(request));
        return ReservationResponse.from(reservationDao.findReservation(id));
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }
}
