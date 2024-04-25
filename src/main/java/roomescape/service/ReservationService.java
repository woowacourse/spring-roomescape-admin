package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationAddRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.reservation.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse addReservation(ReservationAddRequest reservationAddRequest) {
        Reservation reservation = reservationRepository.save(reservationAddRequest.toReservation());
        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }
}
