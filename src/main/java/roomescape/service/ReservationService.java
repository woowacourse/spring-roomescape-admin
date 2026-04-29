package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
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

    public ReservationTimeResponse createReservationTime(ReservationTimeCreateRequest request) {
        Long id = reservationTimeDao.save(request.toEntity());
        return ReservationTimeResponse.fromEntity(reservationTimeDao.findById(id));
    }

    public List<ReservationTimeResponse> readAllReservationTime() {
        return ReservationTimeResponse.fromEntities(reservationTimeDao.findAll());
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
