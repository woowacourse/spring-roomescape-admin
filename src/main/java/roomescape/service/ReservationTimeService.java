package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse addTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime time = reservationTimeRequest.toEntity();
        ReservationTime savedTime = reservationTimeRepository.save(time);
        return ReservationTimeResponse.fromEntity(savedTime);
    }

    public void deleteTime(Long id) {
        boolean isDeleted = reservationTimeRepository.delete(id);
        if (!isDeleted) {
            throw new IllegalStateException("해당하는 ID가 없습니다");
        }
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeRepository.findAll();
    }
}
