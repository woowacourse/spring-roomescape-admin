package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDto save(ReservationTimeRequestDto reservationTimeRequestDto) {
        ReservationTime reservationTime = ReservationTime.create(reservationTimeRequestDto.startAt());
        return ReservationTimeResponseDto.from(reservationTimeRepository.save(reservationTime));
    }

    public ReservationTime findById(Long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 찾는 예약 시간이 없습니다."));
    }

    public List<ReservationTimeResponseDto> findAll() {
        return reservationTimeRepository.finaAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public void deleteById(Long id) {
        reservationTimeRepository.deleteById(id);
    }

}
