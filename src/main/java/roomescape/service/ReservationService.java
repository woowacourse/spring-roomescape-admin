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

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> readReservation() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::toDto)
                .toList();
    }

    public ReservationResponseDto postReservation(ReservationRequestDto requestDto) {
        Reservation newReservation = reservationRepository.save(requestDto.toEntity(), requestDto.timeId());
        return ReservationResponseDto.toDto(newReservation);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
