package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao repository;

    public ReservationTimeService(ReservationTimeDao repository) {
        this.repository = repository;
    }

    public ReservationTimeResponseDto add(ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = new ReservationTime(requestDto.startAt());
        ReservationTime savedReservationTime = repository.save(reservationTime);
        return ReservationTimeResponseDto.toDto(savedReservationTime);
    }

    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTime> times = repository.findAll();
        return times.stream()
            .map(ReservationTimeResponseDto::toDto)
            .toList();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
