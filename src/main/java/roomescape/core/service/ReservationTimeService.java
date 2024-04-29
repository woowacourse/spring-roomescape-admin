package roomescape.core.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.core.dto.ReservationTimeRequest;
import roomescape.core.dto.ReservationTimeResponse;
import roomescape.core.model.ReservationTime;
import roomescape.core.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    public static final int ONE_ROW = 1;
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
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
        return reservationTimeRepository.deleteTime(id) == ONE_ROW;
    }
}
