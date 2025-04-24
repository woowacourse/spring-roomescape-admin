package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationTimeRequest;
import roomescape.reservation.dto.ReservationTimeResponse;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository ReservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository ReservationTimeRepository) {
        this.ReservationTimeRepository = ReservationTimeRepository;
    }

    public ReservationTimeResponse addTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = ReservationTime.createWithoutId(reservationTimeRequest.startAt());
        return ReservationTimeResponse.from(ReservationTimeRepository.insertTime(reservationTime));
    }

    public List<ReservationTimeResponse> getTimes() {
        List<ReservationTime> reservationTimes = ReservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public boolean deleteTimeById(long id) {
        return ReservationTimeRepository.deleteTimeById(id);
    }

    public ReservationTime findTimeById(long id) {
        return ReservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
    }
}
