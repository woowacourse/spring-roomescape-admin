package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.exception.EntityNotFoundException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.repository.ReservationTimeInMemoryRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeInMemoryRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeInMemoryRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponseDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
            .map(ReservationResponseDto::toDto)
            .toList();
    }

    public ReservationResponseDto add(ReservationRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeRepository.findById(requestDto.timeId())
            .orElseThrow(() -> new EntityNotFoundException("선택한 예약 시간이 존재하지 않습니다."));
        Reservation reservation = new Reservation(requestDto.name(), requestDto.date(), reservationTime);

        Reservation saved = reservationRepository.save(reservation);
        return ReservationResponseDto.toDto(saved);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
