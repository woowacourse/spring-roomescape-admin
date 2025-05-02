package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exception.ReservationTimeDuplicateException;
import roomescape.reservation.dto.ReservationTimeRequest;
import roomescape.reservation.dto.ReservationTimeResponse;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository ReservationTimeRepository) {
        this.reservationTimeRepository = ReservationTimeRepository;
    }

    public ReservationTimeResponse addTime(ReservationTimeRequest reservationTimeRequest) {
        if(isSameTimeExist(reservationTimeRequest)) {
            throw new ReservationTimeDuplicateException("이미 같은 시간이 존재합니다.");
        }
        ReservationTime reservationTime = ReservationTime.createWithoutId(reservationTimeRequest.startAt());
        return ReservationTimeResponse.from(reservationTimeRepository.insertTime(reservationTime));
    }

    private boolean isSameTimeExist(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeRepository.findAll().stream()
                .anyMatch(existReservationTime -> existReservationTime.getStartAt().equals(reservationTimeRequest.startAt()));
    }

    public List<ReservationTimeResponse> getTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteTimeById(long id) {
        validateTimeExistenceById(id);
        reservationTimeRepository.deleteTimeById(id);
    }

    public ReservationTime findTimeById(long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
    }

    private void validateTimeExistenceById(long id) {
        if(!reservationTimeRepository.existsTimeById(id)) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
    }
}
