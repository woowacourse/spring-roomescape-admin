package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(roomescape.repository.ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation newReservation = reservationRepository.save(reservationRequest.toReservation());
        return ReservationResponse.from(newReservation);
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
