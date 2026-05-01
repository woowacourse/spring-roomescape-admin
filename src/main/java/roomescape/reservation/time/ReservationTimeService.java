package roomescape.reservation.time;

import org.springframework.stereotype.Service;
import roomescape.reservation.time.dto.ReservationTimeRequestDto;
import roomescape.reservation.time.dto.ReservationTimeResponseDto;
import roomescape.reservation.time.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime save(ReservationTimeRequestDto request) {
        return reservationTimeRepository.save(request.toEntity());
    }

    public void deleteById(long id) {
        int deleteCount = reservationTimeRepository.deleteById(id);
        if (deleteCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 시간 id 입니다 id = " + id);
        }
    }
}
