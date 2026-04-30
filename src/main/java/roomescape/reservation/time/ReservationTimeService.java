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

    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTime> times = reservationTimeRepository.findAll();
        return times.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto save(ReservationTimeRequestDto request) {
        return ReservationTimeResponseDto.from(reservationTimeRepository.save(request.toEntity()));
    }

    public void deleteById(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
