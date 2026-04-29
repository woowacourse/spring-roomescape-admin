package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationDao;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> readAllReservation() {
        return ReservationResponse.fromEntities(reservationDao.findAllReservation());
    }

    public ReservationResponse createReservation(ReservationCreateRequest request) {
        Long id = reservationDao.insertReservation(request.toEntity());
        return ReservationResponse.fromEntity(reservationDao.findReservation(id));
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }
}
