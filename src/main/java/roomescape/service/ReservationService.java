package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequestDto;
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

    public ReservationResponseDto createReservation(ReservationCreateRequestDto dto) {
        ReservationTime reservationTime = reservationTimeRepository.findById(dto.timeId());
        Reservation requestReservation = dto.createWithoutId(reservationTime);
        Reservation newReservation = reservationRepository.save(requestReservation);
        return ReservationResponseDto.from(newReservation, newReservation.time());
    }

    public List<ReservationResponseDto> findAllReservationResponses() {
        List<Reservation> allReservations = reservationRepository.findAll();

        return allReservations.stream()
                .map(reservation -> ReservationResponseDto.from(reservation, reservation.time()))
                .toList();
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
