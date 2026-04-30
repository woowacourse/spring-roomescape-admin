package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.payload.ReservationTimeRequest;
import roomescape.reservation.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime save(ReservationTimeRequest request) {
        Long id = reservationTimeRepository.save(request);
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("예약 시간 저장 후 조회에 실패했습니다. id=" + id));
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteById(Long id) {
        reservationTimeRepository.deleteById(id);
    }

}
