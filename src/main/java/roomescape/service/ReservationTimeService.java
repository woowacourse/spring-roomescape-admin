package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.request.CreateReservationTimeRequest;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.persistence.ReservationTimeDao;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeDao reservationTImeDao;

    public ReservationTimeService(ReservationTimeDao reservationTImeDao) {
        this.reservationTImeDao = reservationTImeDao;
    }

    public Long create(CreateReservationTimeRequest createReservationTimeRequest) {
        return reservationTImeDao.create(new ReservationTime(createReservationTimeRequest.startAt()));
    }

    public ReservationTimeResponse findById(Long reservationTimeId) {
        ReservationTime reservationTime = reservationTImeDao.findById(reservationTimeId)
                .orElseThrow(
                        () -> new IllegalArgumentException(reservationTimeId + "에 해당하는 reservation_time 튜플이 없습니다."));
        return toReservationResponse(reservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTImeDao.findAll();
        return reservationTimes.stream()
                .map(this::toReservationResponse)
                .toList();
    }

    public void deleteById(Long reservationTimeId) {
        reservationTImeDao.deleteById(reservationTimeId);
    }

    private ReservationTimeResponse toReservationResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }
}
