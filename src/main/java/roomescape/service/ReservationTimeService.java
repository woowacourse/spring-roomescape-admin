package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse saveReservationTime(ReservationTimeCreateRequest request) {
        Long id = reservationTimeDao.save(request.toEntity());
        return ReservationTimeResponse.fromEntity(reservationTimeDao.findById(id));
    }

    public List<ReservationTimeResponse> findAllReservationTime() {
        return ReservationTimeResponse.fromEntities(reservationTimeDao.findAll());
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
