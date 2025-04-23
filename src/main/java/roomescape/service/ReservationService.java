package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<ReservationResponseDto> readReservation() {
        return repository.findAll().stream()
                .map(ReservationResponseDto::toDto)
                .toList();
    }

    public ReservationResponseDto postReservation(ReservationRequestDto requestDto) {
        Reservation newReservation = repository.save(requestDto.toEntity(null), requestDto.timeId());
        return ReservationResponseDto.toDto(newReservation);
    }

    public void deleteReservation(long id) {
        repository.deleteById(id);
    }
}
