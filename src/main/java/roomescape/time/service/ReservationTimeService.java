package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.CreateReservationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse create(CreateReservationTimeRequest createReservationTimeRequest) {
        Long id = reservationTimeRepository.save(new ReservationTime(null, createReservationTimeRequest.startAt()));
        ReservationTime reservationTime = reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("예약 시간 생성에 실패했습니다."));
        return ReservationTimeResponse.from(reservationTime);
    }

    public ReservationTimeResponse delete(Long id) {
        ReservationTime reservationTime = reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 예약입니다."));
        reservationTimeRepository.delete(id);
        return ReservationTimeResponse.from(reservationTime);
    }
}
