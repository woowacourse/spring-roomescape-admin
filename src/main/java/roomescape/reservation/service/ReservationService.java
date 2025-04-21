package roomescape.reservation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.utils.ReservationMapper;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public ReservationResponse insert(ReservationRequest reservationRequest) {
        return ReservationMapper.toReservationResponse(reservationDao.insert(reservationRequest));
    }

    public ReservationResponse findById(long id) {
        return ReservationMapper.toReservationResponse(reservationDao.findById(id));
    }

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll().stream()
                .map(ReservationMapper::toReservationResponse)
                .toList();
    }

    public void delete(long id) {
        reservationDao.delete(id);
    }
}
