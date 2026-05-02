package roomescape.domain.service;

import org.springframework.stereotype.Service;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponseDto postReservation(ReservationRequestDto reservationRequestDto) {

        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDto.timeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 시간의 id가 존재하지 않습니다."));

        Reservation reservation = new Reservation(
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationTime
        );
        Long id = reservationRepository.save(reservation);
        return new ReservationResponseDto(
                id,
                reservation.getMemberName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public void deleteReservationBy(Long id) {
        reservationRepository.delete(id);
    }
}
