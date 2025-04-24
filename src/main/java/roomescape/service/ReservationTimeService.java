package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        ReservationTimes reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.getReservationTimes().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    public ReservationTimeResponse saveReservationTime(ReservationTimeRequest request) {
        ReservationTime newReservationTime = request.toReservationTime(null);
        ReservationTime savedReservationTime = reservationTimeDao.save(newReservationTime);

        return new ReservationTimeResponse(savedReservationTime);
    }

    public boolean deleteReservationTime(Long id) {
        return reservationTimeDao.deleteById(id);
    }
}
