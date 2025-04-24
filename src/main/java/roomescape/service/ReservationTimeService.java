package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.request.CreateReservationTimeRequest;
import roomescape.service.response.ReservationTimeResponse;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTImeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTImeRepository) {
        this.reservationTImeRepository = reservationTImeRepository;
    }

    public Long create(CreateReservationTimeRequest createReservationTimeRequest) {
        return reservationTImeRepository.create(new ReservationTime(createReservationTimeRequest.startAt()));
    }

    public ReservationTimeResponse findById(Long reservationTimeId) {
        ReservationTime reservationTime = reservationTImeRepository.findById(reservationTimeId)
                .orElseThrow(
                        () -> new IllegalArgumentException(reservationTimeId + "에 해당하는 reservation_time 튜플이 없습니다."));
        return toReservationResponse(reservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTImeRepository.findAll();
        return reservationTimes.stream()
                .map(this::toReservationResponse)
                .toList();
    }

    public void deleteById(Long reservationTimeId) {
        reservationTImeRepository.deleteById(reservationTimeId);
    }

    private ReservationTimeResponse toReservationResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }
}
