package roomescape.domain.reservation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.dto.CreateReservationRequest;
import roomescape.domain.reservation.dto.CreateReservationResponse;
import roomescape.domain.reservation.dto.ReservationResponse;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public CreateReservationResponse createReservation(CreateReservationRequest request) {
        Reservation savedReservation = reservationRepository.save(request.toEntity());
        return CreateReservationResponse.from(savedReservation);
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findReservation(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservation.delete();
    }
}
