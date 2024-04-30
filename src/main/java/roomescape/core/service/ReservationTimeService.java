package roomescape.core.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.core.domain.ReservationTime;
import roomescape.core.domain.ReservationTimeRepository;
import roomescape.core.service.dto.ReservationTimeRequest;
import roomescape.core.service.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAllReservationTime() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse saveReservationTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = request.toReservationTime();
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public void deleteReservationTime(Long id) {
        ReservationTime reservationTime = findReservationTimeById(id);
        reservationTimeRepository.delete(reservationTime);
    }

    private ReservationTime findReservationTimeById(Long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
    }
}
