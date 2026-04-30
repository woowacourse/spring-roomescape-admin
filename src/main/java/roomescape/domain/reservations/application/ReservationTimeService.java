package roomescape.domain.reservations.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.infrastructure.ReservationTimeRepository;
import roomescape.domain.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.domain.reservations.presentation.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationTimeResponse saveTime(ReservationTimeRequest request) {
        validateSaveRequest(request);
        ReservationTime reservationTime = ReservationTime.of(
                null,
                request.startAt()
        );
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public List<ReservationTime> getTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteTime(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] 예약 날짜 ID가 비어있습니다.");
        }
        reservationTimeRepository.deleteById(id);
    }

    private void validateSaveRequest(ReservationTimeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("[ERROR] 예약 시간 데이터가 비어있습니다.");
        }
        if (request.startAt() == null) {
            throw new IllegalArgumentException("[ERROR] 예약 시간이 비어있습니다.");
        }
    }
}
