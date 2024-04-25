package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.dao.ReservationTimeDao;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeCreateRequest request) {
        final ReservationTime reservationTime = request.toReservationTime();
        final Long id = reservationTimeDao.save(reservationTime);
        return new ReservationTimeResponse(id, reservationTime.getStartAt());
    }

    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::new)
                .collect(Collectors.toList());
    }

    public void deleteReservationTime(final Long id) {
        reservationTimeDao.deleteById(id);
    }
}
