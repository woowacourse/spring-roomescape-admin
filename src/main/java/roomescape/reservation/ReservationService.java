package roomescape.reservation;

import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse create(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        Reservation saved = reservationRepository.save(reservation);
        return ReservationResponse.from(saved);
    }

    public List<ReservationResponse> read() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
