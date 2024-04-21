package roomescape.business;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeResponse;
import roomescape.persistence.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse create(ReservationTime reservationTime) {
        var savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public List<ReservationTimeResponse> getAll() {
        var reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void delete(Long id) {
        var reservationTime = reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약 시간이 없습니다."));
        reservationTimeRepository.deleteById(reservationTime.getId());
    }
}
