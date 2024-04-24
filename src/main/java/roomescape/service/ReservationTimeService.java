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

    public ReservationTimeResponse createTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toEntity();
        Long id = reservationTimeRepository.createTime(reservationTime);
        return new ReservationTimeResponse(id, reservationTime.getStartAt());
    }

    public List<ReservationTimeResponse> readTimes() {
        return reservationTimeRepository.readTimes()
                .stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }

    public boolean deleteTime(Long id) {
        return id.equals(reservationTimeRepository.deleteTime(id));
    }
}
