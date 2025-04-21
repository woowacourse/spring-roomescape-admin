package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.service.dto.ReservationResponse;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationResponse> getReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }
}
