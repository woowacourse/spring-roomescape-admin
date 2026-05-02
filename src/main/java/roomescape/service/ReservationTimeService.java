package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
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
                request.startAt()
        );

        ReservationTime savedReservationTime = reservationTimeDao.insertReservationTime(newReservationTime);

        return convertToResponse(savedReservationTime);
    }

    public void deleteReservationTime(Long id) {
        boolean isReserved = reservationDao.existsByTimeId(id);

        if (isReserved) {
            throw new IllegalArgumentException("이미 예약된 시간은 삭제할 수 없습니다.");
        }

        reservationTimeDao.deleteById(id);
    }

    private ReservationTimeResponse convertToResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt().toString()
        );
    }
}
