package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        return reservationTimes.stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeRequest request) {
        ReservationTime newReservationTime = new ReservationTime(
                null,
                LocalTime.parse(request.startAt())
        );

        ReservationTime savedReservationTime = reservationTimeDao.insertReservationTime(newReservationTime);

        return convertToResponse(savedReservationTime);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.deleteById(id);
    }

    private ReservationTimeResponse convertToResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt().toString()
        );
    }
}
