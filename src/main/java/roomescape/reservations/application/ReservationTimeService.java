package roomescape.reservations.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservations.entity.ReservationRepository;
import roomescape.reservations.entity.ReservationTime;
import roomescape.reservations.entity.ReservationTimeRepository;
import roomescape.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.reservations.presentation.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(
            ReservationTimeRepository reservationTimeRepository, ReservationRepository reservationRepository
    ) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
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

    public List<ReservationTimeResponse> getTimes() {
        List<ReservationTime> times = reservationTimeRepository.findAll();
        return times.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @Transactional
    public void deleteTime(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] 예약 시간 ID가 비어있습니다.");
        }
        if (reservationRepository.existsByReservationTimeId(id)) {
            throw new IllegalStateException("[ERROR] 참조하고 있는 예약 시간이여서 삭제할 수 없습니다.");
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
