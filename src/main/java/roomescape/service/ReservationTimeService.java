package roomescape.service;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.command.ReservationTimeCommand;
import roomescape.service.result.ReservationTimeResult;

@Service
@Transactional(readOnly = true)
@Validated
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationTimeResult register(
            @NotNull(message = "예약 시간 정보가 필요합니다.") ReservationTimeCommand request
    ) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResult.from(saved);
    }

    public List<ReservationTimeResult> getAllReservationTimes() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResult::from)
                .toList();
    }

    @Transactional
    public void remove(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
