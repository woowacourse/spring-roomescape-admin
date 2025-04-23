package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponseDto> readReservation() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::toDto)
                .toList();
    }

    public ReservationResponseDto postReservation(ReservationRequestDto requestDto) {
        timeRepository.existsTimeById(requestDto.timeId());
        Reservation newReservation = reservationRepository.save(requestDto.toEntity(), requestDto.timeId());
        return ReservationResponseDto.toDto(newReservation);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
