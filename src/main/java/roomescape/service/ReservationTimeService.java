package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public List<ReservationTimeResponseDto> readReservationTime() {
        return repository.findAll().stream()
                .map(ReservationTimeResponseDto::toDto)
                .toList();
    }

    public ReservationTimeResponseDto postReservationTime(ReservationTimeRequestDto requestDto) {
        ReservationTime newReservation = repository.save(requestDto.toEntity(null));
        return ReservationTimeResponseDto.toDto(newReservation);
    }

    public void deleteReservationTime(long id) {
        repository.deleteById(id);
    }
}
