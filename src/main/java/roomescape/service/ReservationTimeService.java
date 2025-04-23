package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void validateDuplicateStartTime(LocalTime startAt) {
        if (reservationTimeRepository.existByStartAt(startAt)) {
            throw new IllegalArgumentException("이미 존재하는 예약 시간입니다.");
        }
    }

    public ReservationTime addAndGet(LocalTime startAt) {
        ReservationTime reservationTime = new ReservationTime(startAt);
        return reservationTimeRepository.insertAndGet(reservationTime);
    }
}
