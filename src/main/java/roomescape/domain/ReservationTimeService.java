package roomescape.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.dto.ReservationTimeRequest;
import roomescape.domain.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeRequest request) {
        ReservationTime time = request.toDomain();
        ReservationTime createdTime = reservationTimeRepository.create(time);
        return ReservationTimeResponse.from(createdTime);
    }

    public void deleteReservationTime(Long id) {
        ReservationTime foundReservationTime = reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 예약 시간을 찾을 수 없습니다."));
        reservationTimeRepository.deleteById(foundReservationTime.id());
    }

}
