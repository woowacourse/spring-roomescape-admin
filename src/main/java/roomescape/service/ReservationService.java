package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.dto.ReservationCreateDto;
import roomescape.dto.ReservationResponseDto;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto createReservation(ReservationCreateDto createDto) {
        Reservation reservation = createDto.toDomain();
        Reservation createdReservation = reservationRepository.create(reservation);
        return ReservationResponseDto.from(createdReservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.removeById(id);
    }
}
