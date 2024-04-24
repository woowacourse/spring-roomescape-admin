package roomescape.service.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.reservation.ReservationRepository;
import roomescape.service.reservation.dto.ReservationRequestDto;
import roomescape.service.reservation.dto.ReservationResponseDto;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> findAllReservations() {
        return reservationRepository.findAllReservations()
                .stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        Reservation reservation = reservationRepository.insertReservation(requestDto.toReservation());
        return new ReservationResponseDto(reservation);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteReservationById(id);
    }
}
