package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    public List<ReservationResponse> findAll() {
        List<Reservation> reservationDaoAll = reservationDao.findAll();

        return reservationDaoAll.stream()
                .map(ReservationResponse::toDto)
                .toList();
    }

    public Long create(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());
        Reservation reservation = request.toReservation(reservationTime);
        return reservationDao.create(reservation);
    }

    public int delete(Long id) {
        return reservationDao.delete(id);
    }
}
