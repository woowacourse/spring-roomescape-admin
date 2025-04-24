package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> findAllReservationResponses() {
        List<Reservation> allReservations = reservationRepository.findAll();

        return allReservations.stream()
                .map(reservation -> ReservationResponseDto.from(reservation, reservation.time()))
                .toList();
    }

    public ReservationResponseDto createReservation(ReservationCreateRequestDto dto) {
        Reservation requestReservation = dto.toEntity();
        Reservation newReservation = reservationRepository.save(requestReservation);
        ReservationResponseDto reservationResponseDto = ReservationResponseDto.from(newReservation, newReservation.time());
        return reservationResponseDto;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
