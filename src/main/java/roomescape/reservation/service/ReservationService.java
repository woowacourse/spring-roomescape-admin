package roomescape.reservation.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.entity.ReservationTime;
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
        try {
            ReservationTime time = reservationTimeRepository.findById(requestDto.getTimeId());
            Reservation reservation = Reservation.create(requestDto.getName(), requestDto.getDate(), time);
            return ReservationResponseDto.from(reservationRepository.save(reservation));
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("이미 예약이 존재합니다.");
        }
    }

    public void deleteById(Long id) {
        try {
            reservationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("삭제하려는 예약이 존재하지 않습니다.");
        }
    }

    public List<ReservationResponseDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }
}
