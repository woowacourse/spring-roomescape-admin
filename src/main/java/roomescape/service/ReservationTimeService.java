package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeRepository;

    public ReservationTimeService(ReservationTimeDao reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = request.toEntity();
        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return new ReservationTimeResponse(
                saved.getId(),
                saved.getStartAt()
        );
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(reservationTime -> new ReservationTimeResponse(
                        reservationTime.getId(),
                        reservationTime.getStartAt()
                ))
                .toList();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약 시간을 찾을 수 없습니다."));
        reservationTimeRepository.delete(id);
    }
}
