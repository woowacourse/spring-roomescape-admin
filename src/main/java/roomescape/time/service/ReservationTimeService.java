package roomescape.time.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.entity.ReservationTime;
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
        try {
            reservationTimeRepository.findById(id);
            reservationTimeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("삭제하려는 시간 정보가 존재하지 않습니다. id: " + id);
        }
    }

    public ReservationTimeResponseDto findById(Long id) {
        try {
            ReservationTime time = reservationTimeRepository.findById(id);
            return ReservationTimeResponseDto.from(time);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("해당 ID의 시간 정보를 찾을 수 없습니다. id: " + id);
        }
    }

    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTime> times = reservationTimeRepository.findAll();
        return times.stream()
                .map(ReservationTimeResponseDto::from)
                .collect(Collectors.toList());
    }
}
