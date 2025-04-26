package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeDao.findAll().stream()
                .map(ReservationTimeResponse::toDto)
                .toList();
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toEntity();
        ReservationTime newReservationTime = reservationTimeDao.insert(reservationTime);
        return ReservationTimeResponse.toDto(newReservationTime);
    }

    public boolean deleteReservationTimeById(final Long id) {
        return reservationTimeDao.deleteById(id);
    }
}
