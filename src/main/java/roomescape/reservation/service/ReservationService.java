package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponseDto save(ReservationRequestDto requestDto) {
        ReservationTime time = reservationTimeRepository.findById(requestDto.getTimeId());

        if (reservationRepository.existsByReservation(requestDto.getDate(), requestDto.getTimeId())) {
            throw new IllegalArgumentException("이미 해당 날짜와 시간에 예약이 존재합니다.");
        }

        Reservation reservation = Reservation.create(requestDto.getName(), requestDto.getDate(), time);
        return ReservationResponseDto.from(reservationRepository.save(reservation));
    }

    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제하려는 예약이 존재하지 않습니다.");
        }

        reservationRepository.deleteById(id);
    }

    public List<ReservationResponseDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }
}
