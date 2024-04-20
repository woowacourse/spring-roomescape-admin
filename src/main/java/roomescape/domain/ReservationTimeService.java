package roomescape.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> getAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        return reservationTimeRepository.create(reservationTime);
    }

    public void deleteReservationTime(Long id) {
        ReservationTime foundReservationTime = reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 예약 시간을 찾을 수 없습니다."));
        reservationTimeRepository.deleteById(foundReservationTime.id());
    }
}
