package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(roomescape.repository.ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationDto> findReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationDto::from)
                .toList();
    }

    public ReservationDto createReservation(ReservationRequest reservationRequest) {
        Reservation newReservation = reservationRepository.save(reservationRequest.toReservation());
        return ReservationDto.from(newReservation);
    }

    public boolean deleteReservation(Long id) {
        try {
            reservationRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
