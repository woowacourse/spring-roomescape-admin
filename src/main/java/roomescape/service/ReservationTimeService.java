package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.reservationTime.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private static final String RESERVATION_TIME_NOT_FOUND = "존재하지 않는 예약 시간입니다.";

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime createTime(ReservationTimeCreateRequest request) {
        ReservationTime reservationTime = request.toReservationTime();
        return reservationTimeRepository.save(reservationTime);
    }

    public ReservationTime readReservationTime(Long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_TIME_NOT_FOUND));
    }

    public List<ReservationTime> readReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
