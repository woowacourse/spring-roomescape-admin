package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.reservation.ReservationRequest;
import roomescape.controller.reservation.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        Reservation parsedReservation = reservationRequest.toDomain();
        Reservation savedReservation = reservationRepository.save(parsedReservation);
        return ReservationResponse.from(savedReservation);
    }

    public int deleteReservation(Long id) {
        return reservationRepository.deleteById(id);
    }
}
