package roomescape.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
@Transactional
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimes;
    private final ReservationRepository reservations;

    public ReservationTimeService(ReservationTimeRepository reservationTimes,
                                  ReservationRepository reservations) {
        this.reservationTimes = reservationTimes;
        this.reservations = reservations;
    }

    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime reservationTime = reservationTimes.create(request.toReservationTime());
        return ReservationTimeResponse.from(reservationTime);
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = this.reservationTimes.findAll();
        return convertToReservationTimeResponses(reservationTimes);
    }

    private List<ReservationTimeResponse> convertToReservationTimeResponses(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteById(long id) {
        Optional<ReservationTime> findReservationTime = reservationTimes.findById(id);
        if (findReservationTime.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 시간 입니다.");
        }
        long reservedCount = reservations.findReservationCountByTimeId(id);
        if (reservedCount > 0) {
            throw new IllegalStateException(String.format("해당 예약 시간에 연관된 예약이 존재하여 삭제할 수 없습니다. 삭제 요청한 시간:%s",
                    findReservationTime.get().getStartAt()));
        }
        reservationTimes.deleteById(id);
    }
}
