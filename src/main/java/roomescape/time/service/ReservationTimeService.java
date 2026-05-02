package roomescape.time.service;

import org.springframework.stereotype.Service;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDto save(ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = ReservationTime.create(requestDto.getStartAt());
        return ReservationTimeResponseDto.from(reservationTimeRepository.save(reservationTime));
    }

    public void deleteById(Long id) {
        if (!reservationTimeRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }

        reservationTimeRepository.deleteById(id);
    }

    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTime> times = reservationTimeRepository.findAll();
        return times.stream()
                .map(ReservationTimeResponseDto::from)
                .collect(Collectors.toList());
    }
}
