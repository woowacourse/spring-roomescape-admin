package roomescape.service;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Service
@Transactional
@Validated
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse register(
            @NotNull(message = "예약 시간 정보가 필요합니다.") ReservationTimeRequest request
    ) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void remove(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
